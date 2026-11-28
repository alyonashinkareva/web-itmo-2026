<#import "commons.ftlh" as c>

<@c.page>
    <#if profile??>
        <h1>${profile.login}</h1>
        <dl>
            <dt>Id</dt><dd>${profile.id?c}</dd>
            <dt>Login</dt><dd>${profile.login}</dd>
            <dt>Created</dt><dd>${profile.creationTime?datetime}</dd>
        </dl>
    <#else>
        <p>No such user</p>
    </#if>
</@c.page>
