$(document).ready(function () {

    // --- 1. Загрузка данных для форм ---
    let masters = [];
    let clients = [];
    let services = [];
    let resources = [];

    // Загрузка мастеров
    $.get('/api/masters', function (data) {
        masters = data;
        fillSelect('#masterId', masters, 'fullName', 'id');
        fillSelect('#filterMaster', masters, 'fullName', 'id');
    });

    // Загрузка клиентов
    $.get('/api/clients', function (data) {
        clients = data;
        fillSelect('#clientId', clients, 'fullName', 'id');
    });

    // Загрузка услуг
    $.get('/api/services', function (data) {
        services = data;
        fillSelect('#serviceId', services, 'name', 'id');
    });

    // Загрузка ресурсов
    $.get('/api/resources', function (data) {
        resources = data;
        fillSelect('#resourceId', resources, 'name', 'id');
    });

    // Функция заполнения select
    function fillSelect(selectId, items, labelProp, valueProp) {
        const $select = $(selectId);
        $select.empty().append('<option value="">Выберите...</option>');
        items.forEach(item => {
            $select.append(`<option value="${item[valueProp]}">${item[labelProp]}</option>`);
        });
    }

    // --- 2. Обновление таблицы записей ---
    let table = $('#appointmentsTable').DataTable({
        "language": {
            "url": "https://cdn.datatables.net/plug-ins/1.13.6/i18n/ru.json"
        },
        "paging": true,
        "searching": true,
        "ordering": true,
        "info": true,
        "pageLength": 10,
        "lengthMenu": [10, 25, 50]
    });

    function loadAppointments(masterId = '', date = '') {
        let url = '/api/appointments';
        if (masterId || date) {
            url += '?';
            if (masterId) url += `masterId=${masterId}`;
            if (masterId && date) url += '&';
            if (date) url += `date=${date}`;
        }

        $.get(url, function (data) {
            table.clear().draw();
            data.forEach(app => {
                const resource = app.resource ? app.resource.name : '—';
                const statusClass = app.status === 'CONFIRMED' ? 'badge bg-success' :
                    app.status === 'PENDING' ? 'badge bg-warning' :
                        app.status === 'CANCELLED' ? 'badge bg-danger' : 'badge bg-secondary';

                const actions = `
                    <button class="btn btn-sm btn-outline-danger delete-btn" data-id="${app.id}">
                        <i class="fas fa-trash"></i>
                    </button>
                `;

                table.row.add([
                    app.client.fullName,
                    app.master.fullName,
                    app.service.name,
                    resource,
                    new Date(app.startTime).toLocaleString('ru-RU'),
                    `<span class="${statusClass}">${app.status}</span>`,
                    actions
                ]).draw();
            });
        });
    }

    // Загрузить все записи при старте
    loadAppointments();

    // Применить фильтр
    $('#applyFilter').click(function () {
        const masterId = $('#filterMaster').val();
        const date = $('#filterDate').val();
        loadAppointments(masterId, date);
    });

    // Очистить фильтр
    $('#filterMaster, #filterDate').change(function () {
        if ($('#filterMaster').val() === '' && $('#filterDate').val() === '') {
            loadAppointments();
        }
    });

    // --- 3. Создание записи ---
    $('#saveAppointmentBtn').click(function () {
        const formData = {
            clientId: $('#clientId').val(),
            masterId: $('#masterId').val(),
            serviceId: $('#serviceId').val(),
            resourceId: $('#resourceId').val() === '' ? null : $('#resourceId').val(),
            startTime: $('#startTime').val(),
            notes: $('#notes').val()
        };

        if (!formData.clientId || !formData.masterId || !formData.serviceId || !formData.startTime) {
            $('#warningMessage').removeClass('d-none').text('Заполните все обязательные поля!');
            return;
        }

        $.ajax({
            url: '/api/appointments',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function () {
                $('#addAppointmentModal').modal('hide');
                $('#addAppointmentForm')[0].reset();
                $('#warningMessage').addClass('d-none');
                loadAppointments(); // Обновить таблицу
                alert('Запись успешно создана!');
            },
            error: function (xhr) {
                const error = xhr.responseJSON?.message || 'Ошибка создания записи';
                $('#warningMessage').removeClass('d-none').text(error);
            }
        });
    });

    // --- 4. Удаление записи ---
    $(document).on('click', '.delete-btn', function () {
        const id = $(this).data('id');
        if (confirm('Вы уверены, что хотите удалить эту запись?')) {
            $.ajax({
                url: `/api/appointments/${id}`,
                type: 'DELETE',
                success: function () {
                    loadAppointments();
                }
            });
        }
    });

    // --- 5. Автоматически заполнять время при выборе услуги ---
    $('#serviceId').change(function () {
        const serviceId = $(this).val();
        if (!serviceId) return;

        const service = services.find(s => s.id == serviceId);
        if (service) {
            const duration = service.durationMinutes;
            const now = new Date();
            const start = new Date(now.getTime() + (now.getTimezoneOffset() * 60000)); // Убираем смещение
            const end = new Date(start.getTime() + duration * 60000);

            // Установить время начала на ближайший 15-минутный интервал
            start.setMinutes(Math.ceil(start.getMinutes() / 15) * 15, 0, 0);
            $('#startTime').val(start.toISOString().slice(0, 16));
        }
    });

    // --- 6. Автоматически обновлять доступные ресурсы при выборе услуги ---
    $('#serviceId').change(function () {
        const serviceId = $(this).val();
        if (!serviceId) {
            $('#resourceId').prop('disabled', false).val('');
            return;
        }

        const service = services.find(s => s.id == serviceId);
        const resourceIds = service?.requiredResources?.map(r => r.id) || [];

        $('#resourceId').empty().append('<option value="">Не требуется</option>');
        resources.forEach(r => {
            if (resourceIds.includes(r.id)) {
                $('#resourceId').append(`<option value="${r.id}">${r.name}</option>`);
            }
        });
        if (resourceIds.length === 0) {
            $('#resourceId').prop('disabled', true);
        } else {
            $('#resourceId').prop('disabled', false);
        }
    });

});