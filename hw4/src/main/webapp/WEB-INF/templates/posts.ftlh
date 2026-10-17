<#import "./common.ftlh" as c>


<@c.page>
    <#list user_posts as post>
        <@c.post post/>
    </#list>
</@c.page>