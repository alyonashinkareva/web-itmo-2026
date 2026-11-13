<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id??>
        <#assign u = c.findBy(users, "id", user_id?number)!/>
    <#else>
        <#assign u = c.findBy(users, "handle", handle)!/>
    </#if>

    <#if u?has_content>
<#--        ${u.handle} / ${u.name} (${u.id})-->
        <@userDisplay u/>
        <@arrays u/>
    <#else>
        No such user
    </#if>
</@c.page>

<#macro userDisplay user>
    <#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <div class="userDisplay">
        <section>
            <h2><@c.userlink user/></h2>
        </section>
        <section>
            <ul>
                <li>id: ${user.id}</li>
                <li>handle: ${user.handle}</li>
                <li>name: ${user.name}</li>
            </ul>
        </section>
        <section class="ava">
            <img alt="photo" title="photo" src="/img/photo_2026-10-16_21-14-03.jpg">
        </section>
    </div>
</#macro>

<#macro arrays user>
    <#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->

    <#list users as u>
        <#if u.id == user.id>
            <div class="arrays">
                <#if u?index gt 0>
                    <a href="/user?handle=${users[u?index - 1].handle}">←</a>
                <#else>
                    <span class="disabled">←</span>
                </#if>

                <#if u?index lt users?size - 1>
                    <a href="/user?handle=${users[u?index + 1].handle}">→</a>
                <#else>
                    <span class="disabled">→</span>
                </#if>
            </div>
        </#if>
    </#list>
</#macro>
