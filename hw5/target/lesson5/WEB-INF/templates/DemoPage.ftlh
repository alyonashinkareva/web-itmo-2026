<#import "./common.ftlh" as c>

<@c.page>
    <p>${message}</p>

    <ul>
        <li><a href="/demo">Page method</a></li>
        <li><a href="/demo?action=fromParent">Parent method</a></li>
        <li><a href="/demo?action=fromInterface">Interface method</a></li>
    </ul>
</@c.page>