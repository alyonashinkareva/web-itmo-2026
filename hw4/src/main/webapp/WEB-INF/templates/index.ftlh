<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
       <@c.post currentPost=post isFull=false/>
    </#list>
</@c.page>
