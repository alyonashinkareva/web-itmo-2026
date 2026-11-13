// Всплывающее уведомление в правом нижнем углу.
window.notify = function (message, isError) {
    $.notify(message, {
        position: "right bottom",
        className: isError ? "error" : "success"
    });
};

window.ajax = function (settings) {
    $.ajax($.extend({
        type: "POST",
        dataType: "json",
        error: function () {
            notify("Server error", true);
        }
    }, settings, {
        success: function (response) {
            if (response.redirect) {
                location.href = response.redirect;
            } else if (settings.success) {
                settings.success(response);
            }
        }
    }));
};