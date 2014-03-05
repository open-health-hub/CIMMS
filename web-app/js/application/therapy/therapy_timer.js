


    
    
    var timer;
    var therapySession = new TherapySession();

    
    
    
    $(function() {
        $('#clockStartStop').bind('click', function() {
            $('#clockStartStop').text('Stop');
            startTimer();
        });        
    });
    
    function stopTimer() {
        $('#clockStartStop').unbind('click');
        $('#clockStartStop').text('Start');
        $('#clockStartStop').addClass('btn-success');
        $('#clockStartStop').removeClass('btn-danger');
        
        $('#clockStartStop').bind('click', function() {            
            startTimer();
        });
        
        clearTimeout(timer); 
    }

    function startTimer() {
            	
        $('#clockStartStop').unbind('click');
        $('#clockStartStop').text('Stop');
        $('#clockStartStop').addClass('btn-danger');
        $('#clockStartStop').removeClass('btn-success');

        
        $('#clockStartStop').bind('click', function() {            
            stopTimer();
        }); 
        
        therapySession.beginNewPeriod();
        showTime();
    }

    function showTime() {
        
        therapySession.updateTherapyDuration();        
        
        var duration = therapySession.getFormattedDuration();
        
        $("#clock").text(duration);
        $("#therapyDuration").val(therapySession.therapyDuration);
        timer=setTimeout('showTime()',1000);      
     
    }
 
    /* ----- */
    /* CLOCK */
    /* ----- */

    function TherapySession() {
    	this.timeAccumulatedLastPeriod = 0;
        this.periodStartTime;
        this.therapyDuration = 0;
        

        this.updateTherapyDuration = updateTherapyDuration;
        this.beginNewPeriod = beginNewPeriod;
        this.getFormattedDuration = getFormattedDuration;
    }
    
    
    function updateTherapyDuration() {
        var now=new Date();        
        this.therapyDuration = this.timeAccumulatedLastPeriod + (now.getTime() - this.periodStartTime.getTime());        
    }

    function beginNewPeriod() {
    	this.timeAccumulatedLastPeriod = this.therapyDuration;
        this.periodStartTime = new Date();
    }
    
    function getFormattedDuration() {
        return format(this.therapyDuration);
    }
    
    function format(msecs) {
        var d = new Date(msecs);
        
        var h=zeroPad(d.getHours());
        var m=zeroPad(d.getMinutes());
        var s=zeroPad(d.getSeconds());
        
        return h+":"+m+":"+s;
    }
    
    function zeroPad(i) {    
        if (i<10) {
            i="0" + i;
        }
        return i;
    }
    
    