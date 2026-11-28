<#import "commons.ftlh" as c>

<@c.page>
    <#assign isAdmin = user?? && user.admin && !user.disabled>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if isAdmin>
                    <th>Actions</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="${isAdmin?then(4, 3)}">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                    <tr>
                        <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>
                        <#if isAdmin>
                            <td>
                                <button type="button"
                                        onclick="changeDisabled(
                                            ${user.id?c},
                                            ${(!user.disabled)?c}
                                        )">${user.disabled?then("Enable", "Disable")}
                                </button>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>

    <#if isAdmin>
        <form id="disabledForm"
              method="post"
              action="/users/all">

            <input type="hidden" name="id" id="userId">
            <input type="hidden" name="disabled" id="userDisabled">
        </form>

        <script>
            function changeDisabled(id, disabled) {
                document.getElementById("userId").value = id;
                document.getElementById("userDisabled").value = disabled;
                document.getElementById("disabledForm").submit();
            }
        </script>
    </#if>
</@c.page>