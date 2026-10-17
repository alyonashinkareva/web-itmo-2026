<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.post post=post/>
    </#list>
</@c.page>
