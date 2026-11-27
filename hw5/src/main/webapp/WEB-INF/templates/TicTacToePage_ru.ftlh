<#-- @ftlvariable name="state" type="ru.itmo.wp.web.page.TicTacToePage.State" -->
<#import "./common.ftlh" as c>

<@c.page>
    <h1>Крестики-нолики</h1>

    <#list 0..state.size-1 as row>
        <div class="tictactoe-row">
            <#list 0..state.size-1 as col>
                <form class="tictactoe-form" method="post">
                    <input type="hidden" name="action" value="onMove">
                    <input type="submit" <#if state.cells[row][col]?has_content>disabled</#if>
                           class="tictactoe-cell"
                           name="cell_${row}${col}" value="${state.cells[row][col]!" "}">
                </form>
            </#list>
        </div>
    </#list>

    <#assign phase=state.phase/>

    <div class="tictactoe-message">
        <#if phase=="RUNNING">
            Ход: <#if state.crossesMove>X<#else>O</#if>
        <#elseif phase=="WON_X">
            Игра завершена, победил X!
        <#elseif phase=="WON_O">
            Игра завершена, победил O!
        <#elseif phase=="DRAW">
            Игра завершена, ничья!
        </#if>
    </div>

    <#if phase!="RUNNING">
        <form method="post">
            <input type="hidden" name="action" value="newGame">
            <input type="submit" class="tictactoe-new-game" name="newGame" value="Новая игра">
        </form>
    </#if>
</@c.page>
