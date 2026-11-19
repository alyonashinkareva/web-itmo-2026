<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#else>
        <#if user_id?? && user_id?is_number>
            <#assign u = c.findBy(users, "id", user_id)!/>
        </#if>
    </#if>


    <#if u?has_content>
        <#list users as user>
            <#if u.id == user.id>
                <#assign pos = user?index/>
            </#if>
        </#list>

        <div class="profile">
            <h1><@c.userlink u/></h1>
            <ul>
                <li>id: ${u.id}</li>
                <li>handle: ${u.handle}</li>
                <li>name: ${u.name}</li>
                <#assign countPosts = 0>
                <#list posts as post>
                    <#if post.user_id == u.id>
                        <#assign countPosts = countPosts + 1>
                    </#if>
                </#list>
                <a href="/posts?user_id=${u.id}"><li>Posts: ${countPosts}</li></a>
            </ul>
            <img src="/img/avatar.png" alt="avatar"/>
        </div>

        <div class="navigate">
            <#if pos == 0>
                <span>←</span>
             <#else>
                <a href="/user?handle=${users[pos - 1].handle}">←</a>
            </#if>

            <#if pos + 1 == users?size>
                <span>→</span>
             <#else>
                <a href="/user?handle=${users[pos + 1].handle}">→</a>
            </#if>
        </div>

    <#else>
        No such user
    </#if>
</@c.page>
