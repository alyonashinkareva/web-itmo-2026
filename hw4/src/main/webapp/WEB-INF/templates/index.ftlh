<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?sort_by("id")?reverse  as post>
       <@c.post currentPost=post isFull=false/>
    </#list>
</@c.page>
