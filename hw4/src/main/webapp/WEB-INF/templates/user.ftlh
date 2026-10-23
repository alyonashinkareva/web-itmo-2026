<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <div class="user">
        <#assign u =
            (c.findBy(users, "handle", handle!""))!
            (c.findBy(users, "id", user_id!(-1)))!""
        />

        <#if u?has_content>
            <div class="profile">
                <div class="info">
                    <@c.userlink u/>
                    <#assign info=[
                                {"key": "id", "value": u.id},
                                {"key": "handle", "value": u.handle},
                                {"key": "name", "value": u.name}]/>
                    <ul>
                        <#list info as item_info>
                            <li>${item_info.key}: ${item_info.value}</li>
                        </#list>
                    </ul>
                </div>
                <div class="avatar">
                    <img alt="Avatar" title="Avatar" src="/img/no-avatar.jpg"/>
                </div>
            </div>
            <div class="nav">
                <@c.arrow "←" c.findPrevBy(users, "handle", u.handle)!""/> 
                <@c.arrow "→" c.findNextBy(users, "handle", u.handle)!""/> 
            </div>
        <#else>
            No such user
        </#if>
    </div>
</@c.page>
