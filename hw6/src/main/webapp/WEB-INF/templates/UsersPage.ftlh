<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if isAdmin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td>${u.login}</td>
                    <td>${u.creationTime?datetime}</td>
                    <#if isAdmin>
                        <td>
                            <span class="admin-value">${u.admin?c}</span>
                            <button data-user-id="${u.id?c}" data-admin="${u.admin?c}">${u.admin?then("Disable", "Enable")}</button>
                        </td>
                    </#if>
                </tr>
            <#else>
                <tr class="noData">
                    <td colspan="${isAdmin?then(4, 3)}">No data</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".users button").click(function () {
                const $button = $(this);
                const admin = !$button.data("admin");
                ajax({
                    data: {action: "setAdmin", userId: $button.data("user-id"), admin: admin},
                    success: function (response) {
                        if (response.error) {
                            notify(response.error, true);
                        } else {
                            $button.data("admin", admin);
                            $button.text(admin ? "Disable" : "Enable");
                            $button.siblings(".admin-value").text(String(admin));
                        }
                    }
                });
            });
        });
    </script>
</@c.page>
