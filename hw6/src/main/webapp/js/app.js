// Всплывающее уведомление в правом нижнем углу.
window.notify = function (message, isError) {
    $.notify(message, {
        position: "right bottom",
        className: isError ? "error" : "success"
    });
};

function ajax(options) {
    var success = options.success;
    var settings = $.extend({
        type: "POST",
        dataType: "json",
        error: function () {
            notify("Request failed. Please try again.", true);
        }
    }, options);

    settings.success = function (response) {
        if (response.redirect) {
            location.href = response.redirect;
        } else if (success) {
            success(response);
        }
    };

    return $.ajax(settings);
}

$(function () {
    $(".ajax-form").on("submit", function (event) {
        event.preventDefault();

        var form = $(this);
        var error = form.find(".error");
        var submit = form.find(":submit");
        error.text("");
        submit.prop("disabled", true);

        ajax({
            url: form.attr("action"),
            data: form.serialize(),
            success: function (response) {
                error.text(response.error || "");
            },
            error: function () {
                error.text("Request failed. Please try again.");
            }
        }).always(function () {
            submit.prop("disabled", false);
        });
    });
});
