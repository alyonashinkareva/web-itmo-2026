<#import "./common.ftlh" as c>
<#-- @ftlvariable name="currPost" type="ru.itmo.web.hw4.model.Post" -->

<@c.page>
    <#if currPost??>
        <@c.post currPost true/>
    <#else>
        <p>Post not found</p>
    </#if>
</@c.page>