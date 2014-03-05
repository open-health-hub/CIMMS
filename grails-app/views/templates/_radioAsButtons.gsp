<script type="text/x-jquery-tmpl" id="radioGroupAsButtons">
    <div class="  span-4" style="margin-left:25px; vertical-align:2px;">
			<input data-bind='checkedWithToggle: nutritionManagement.adequateAt72, uniqueId:true, uniqueName:true'   type='radio' value="Yes"/>
    	<label style="width: 125px; margin-right: 5px; margin-top: 1px; vertical-align: top;" data-bind="uniqueFor:true" >&lt; 95%</label>
		</div>
		<div class=" span-4" style="vertical-align:2px;">
    	<input data-bind='checkedWithToggle: nutritionManagement.adequateAt72, uniqueId:true, uniqueNameForRadio:true'    type='radio' value="No"/>
			<label style="width: 125px; margin-right: 5px; margin-top: 1px; vertical-align: top;" data-bind="uniqueFor:true" >&gt; 95%</label>
		</div>
</script>