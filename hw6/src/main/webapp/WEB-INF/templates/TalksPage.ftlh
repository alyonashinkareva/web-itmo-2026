<#import "commons.ftlh" as c>

<@c.page>
    <div class="send-talk form-box">
        <div class="header">Send message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="send"/>
                <div class="field">
                    <div class="name">
                        <label for="targetUserId">To</label>
                    </div>
                    <div class="value">
                        <select id="targetUserId" name="targetUserId">
                            <#list users as u>
                                <option value="${u.id?c}">${u.login}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" rows="4"></textarea>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
    </div>

    <div class="talks datatable">
        <div class="caption">Talks</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>From</th>
                <th>To</th>
                <th>Text</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as t>
                <tr>
                    <td>${t.id?c}</td>
                    <td>${usersById[t.sourceUserId?c].login}</td>
                    <td>${usersById[t.targetUserId?c].login}</td>
                    <td>${t.text}</td>
                    <td>${t.creationTime?datetime}</td>
                </tr>
            <#else>
                <tr class="noData">
                    <td colspan="5">No data</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".send-talk form").submit(function () {
                const $error = $(this).find(".error");
                ajax({
                    data: $(this).serialize(),
                    success: function (response) {
                        $error.text(response.error || "");
                    }
                });
                return false;
            });
        });
    </script>
</@c.page>
