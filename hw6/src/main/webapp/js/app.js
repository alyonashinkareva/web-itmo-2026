// Всплывающее уведомление в правом нижнем углу.
window.notify = function (message, isError) {
    $.notify(message, {
        position: "right bottom",
        className: isError ? "error" : "success"
    });
};

window.ajax = function (data, success_fun) {
    return $.ajax({
        data,
        method: "POST",
        dataType: "json",
        success: function (response) {
            if (response["redirect"]) {
                location.href = response["redirect"];
                return;
            }

            if (success_fun) {
                success_fun(response);
            }
        }
    });
};