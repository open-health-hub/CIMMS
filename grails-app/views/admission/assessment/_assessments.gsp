<div class="form-container">
    <form class="form-horizontal">

        <div id="preMorbidBarthelKnown"
             data-bind="inError:'therapyManagement.assessmentManagement.barthelAssessments.manualTotal therapyManagement.baselineAssessmentManagement.barthel.bowels therapyManagement.baselineAssessmentManagement.barthel.bladder therapyManagement.baselineAssessmentManagement.barthel.grooming therapyManagement.baselineAssessmentManagement.barthel.toilet therapyManagement.baselineAssessmentManagement.barthel.feeding therapyManagement.baselineAssessmentManagement.barthel.transfer therapyManagement.baselineAssessmentManagement.barthel.mobility therapyManagement.baselineAssessmentManagement.barthel.dressing therapyManagement.baselineAssessmentManagement.barthel.stairs therapyManagement.baselineAssessmentManagement.barthel.bathing'">
            <div class="control-group">
                <label class="control-label" for="preMorbidManualBarthelScore">Barthel Score</label>

                <div class="controls">

                    <input class="input-mini"
                           id="preMorbidManualBarthelScore"
                           name="preMorbidManualBarthelScore"
                           data-bind="value: admissionDetails().preMorbidAssessment().assessments().barthel().manualTotal, visible: admissionDetails().preMorbidAssessment().assessments().barthel().hasDetail() != true"
                    />

                    <g:if test="${grailsApplication.config.ssnap.level == 'standard'}">
                        <div>
                            <label for="preMorbidBarthelIsUnknown">Not known</label>
                            <g:checkBox data-bind="checkBoxAsButton: admissionDetails().preMorbidAssessment().assessments().barthelNotKnown"
                                        name="preMorbidBarthelIsUnknown"
                                        value=""/>
                        </div>
                    </g:if>

                    <input  name="preMorbidCalculatedBarthelScore" id="preMorbidCalculatedBarthelScore"
                            disabled="true"
                            data-bind="value: theBarthelScore, visible: admissionDetails().preMorbidAssessment().assessments().barthel().hasDetail() === true"
                    />

                    <span id="editPreMorbidBarthelLink" class="help-block">
                        Click <strong><a id="editPreMorbidBarthelScore" data-bind="click: editBarthel ">here</a></strong> to enter details
                    </span>
                </div>

            </div>

        </div>


        <div id="preMorbidModifiedRankinKnown"
             data-bind="inError:'therapyManagement.assessmentManagement.modifiedRankinAssessments.modifiedRankinScore' ">

            <div class="control-group">
                <label class="control-label" for="preMorbidModifiedRankinScore">Modified Rankin Score</label>

                <div class="controls">
                    <input class="input-mini" name="preMorbidModifiedRankinScore" id="preMorbidModifiedRankinScore"
                           data-bind="value: admissionDetails().preMorbidAssessment().assessments().modifiedRankin().modifiedRankinScore"/>
                    <span class="help-block" id="editPreMorbidModifiedRankinLink">
                        Click <strong><a id="editPreMorbidModifiedRankinScore" data-bind="click: editModifiedRankin ">here</a></strong> to enter details
                    </span>
                </div>
            </div>

            <%--div class="span-5 ">
                <g:checkBox
                    data-bind="checkBoxAsButton: admissionDetails().preMorbidAssessment().assessments().modifiedRankinNotKnown"
                    name="preMorbidModifiedRankinUnknown"
                    value="" />
                <label style="font-size: 10px" for="preMorbidModifiedRankinUnknown">Not known</label>
            </div--%>

        </div>
    </form>
</div>

<g:render template="/admission/assessment/dialog/barthel"
          model="['careActivityInstance': careActivityInstance, 'errorList': errorList]"/>

<g:render template="/admission/assessment/dialog/modifiedRankin"
          model="['careActivityInstance': careActivityInstance, 'errorList': errorList]"/>
