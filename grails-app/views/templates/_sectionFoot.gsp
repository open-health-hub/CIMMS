

<br>

<div class="scroller"  data-bind="visible: tracker().somethingHasChanged" >
	<button data-bind="click: undo, enableButtons: tracker().somethingHasChanged ">Undo</button>		
	<button data-bind="click: save, enableButtons: tracker().somethingHasChanged ">Save</button>
</div>

<br>

<div class="alert-container" data-bind="visible: infoMessage().length != 0 ">
    <span data-bind="html: infoMessage" ></span>    
</div>

