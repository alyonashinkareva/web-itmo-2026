<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if showAdmin><th>Admin</th></#if>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id?c}</td>
                    <td>${u.login}</td>
                    <td>${u.creationTime?datetime}</td>
                    <#if showAdmin>
                        <td>
                            <span class="admin-status">${u.admin?c}</span>
                            <button type="button" class="change-admin" data-id="${u.id?c}"
                                    data-admin="${(!u.admin)?c}">${u.admin?string("Disable", "Enable")}</button>
                        </td>
                    </#if>
                </tr>
            <#else>
                <tr class="noData">
                    <td colspan="${showAdmin?string('4', '3')}">No data</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <#if showAdmin>
        <script>
            $(function () {
                $(".change-admin").on("click", function () {
                    var button = $(this);
                    button.prop("disabled", true);

                    ajax({
                        url: "/users",
                        data: {
                            action: "updateAdmin",
                            id: button.attr("data-id"),
                            admin: button.attr("data-admin")
                        },
                        success: function (response) {
                            if (response.error) {
                                notify(response.error, true);
                                return;
                            }
                            button.siblings(".admin-status").text(String(response.admin));
                            button.text(response.admin ? "Disable" : "Enable");
                            button.attr("data-admin", String(!response.admin));
                        }
                    }).always(function () {
                        button.prop("disabled", false);
                    });
                });
            });
        </script>
    </#if>
</@c.page>
