<#import "commons.ftlh" as c>

<@c.page>
    <#if userProfile??>
        <div class="user-profile">
            <ul>
                <li> id: ${userProfile.id} </li>
                <li> login: ${userProfile.login} </li>
                <li> creation time: ${userProfile.creationTime} </li>
            </ul>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
