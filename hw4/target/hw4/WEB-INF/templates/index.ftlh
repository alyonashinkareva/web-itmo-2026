<#import "./common.ftlh" as c>

<@c.page>
    <#list posts as p>
        <@c.post p />
    </#list>
</@c.page>
