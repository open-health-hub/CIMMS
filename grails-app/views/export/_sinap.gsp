<p> </p>

<b/>
<b/>


            <div class="list">
                <table>
                   
                    <tbody>
                    <g:each in="${lines}" status="i" var="line">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${line}</td>
                        
             
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>