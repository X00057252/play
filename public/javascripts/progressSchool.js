/* ===========================================================

	IT Tallaght, 
	Bart Bula, X00107883, 
	Andro Haavandi, X00057252
	April 2015 

=========================================================== */



/* Governing read more/less links in articles: --
 		shID+'show' - "Read more" link
		shID - Hidden section          */  
		
function showHide(shID) {
	var top = document.getElementById(shID+'-top').offsetTop; //Getting position of top of article
	if (document.getElementById(shID)) {
		//if Read More link visible - hide Read More link, display hidden section:
		if (document.getElementById(shID+'-show').style.display != 'none') {
			document.getElementById(shID+'-show').style.display = 'none';
			document.getElementById(shID).style.display = 'block';
		}
		else {
		// Read More link not visible - show Read More link, hide hidden section:	
			document.getElementById(shID+'-show').style.display = 'inline';
			document.getElementById(shID).style.display = 'none';
			window.scrollTo(0, top-250);   //Go to the top of the article (-250 so it displays slightly lower).
		}
	}
}
//-- End of: Governing read more/less links in articles --





// Check if email and confirmed email are the same: --

function emailCheck(input) {
	// if confirmation email different from email - display error message:
	if (input.value != document.getElementById("email").value) {
		document.getElementById("emailConf").setCustomValidity("Emails don't match! Please re-enter.");;
	}
	else{
	// clear error message:
		document.getElementById("emailConf").setCustomValidity('');
	}
}
// End of: Check if email and confirmed email are the same --




    $(function() {
        if($('#selectedCourseId').val())
        {
            $('#courseSelect').val($('#selectedCourseId').val())
        }
        $('#studentCourseForm').submit(function(event) {
            event.preventDefault();
            if($('#emailConf').val() != $('#emailConf').val())
            {
                $('#message').text("Emails don't match! Please re-enter.").show().delay(5000).fadeOut();
            }
            else if($('#passwordStu').val() != $('#passwordConf').val())
            {
                $('#message').text("Passwords don't match! Please re-enter.").show().delay(5000).fadeOut();
            }
            else
            {
                applyCourse(event.originalEvent.explicitOriginalTarget.getAttribute('action'));
            }
        });

        $('#accountUpdateForm').submit(function(event) {
            event.preventDefault();
            if($('#emailConf').val() != $('#emailConf').val())
            {
                $('#message').text("Emails don't match! Please re-enter.").show().delay(5000).fadeOut();
            }
            else if($('#passwordStu').val() != $('#passwordConf').val())
            {
                $('#message').text("Passwords don't match! Please re-enter.").show().delay(5000).fadeOut();
            }
            else
            {
                updateAccount();
            }
        });
    });

    function updateAccount()
    {
        var data = accountData();
        var successHandler = function(){ $('#message').text('Account update is successful').show().delay(5000).fadeOut();}
        var failureHandler = function(xhr, status, error){ $('#message').text('Course Save is failed with ' + error).show().delay(5000).fadeOut();}
        ajaxHandler('updateProfile', 'POST', data, successHandler, failureHandler);
    }

    function submitEvent(eventId, studentId){
        var action = $('#action'+eventId).val();
        var url = (action == 'book' ? ('/bookEvent/student/'+studentId+'/event/'+eventId) : ('/unBookEvent/student/'+studentId+'/event/'+eventId));
        var successHandler = function(data){
                                $('#message1').text('Action successful').show().delay(5000).fadeOut();
                                $('#action'+eventId).val(action == 'book' ? 'unbook' : 'book');
                                $('#eventSubmitBtn'+eventId).text(action == 'book' ? 'Un Book Event' : 'Book Event');
                                $('#currentCapacity'+eventId).html($.parseJSON(data)['freeSpace']);
                             }
        var failureHandler = function(xhr, status, error){
         $('#message1').text('Action failed with error ' + xhr.responseText).show().delay(5000).fadeOut();
        }
        ajaxHandler(url, 'POST', null, successHandler, failureHandler);
    }

    function applyCourse(action)
    {
        var data = accountData();
        data['courseId'] = $('#courseSelect').val();
        data['courseStartDate'] = $('#courseStartDate').val();
        data['accomodationType'] = $('[name=accommOption]:checked').val();
        var successHandler = function(){ $('#message').text('Course Save is successful').show().delay(5000).fadeOut();
                                        if(action == 'save')
                                        {
                                            window.location="/account"
                                        }
                                        else
                                        {
                                            window.location="/paymentgpg?courseId="+data['courseId']
                                        }
                                        }
        var failureHandler = function(xhr, status, error){ $('#message').text('Course Save is failed with ' + error).show().delay(5000).fadeOut();}
        ajaxHandler(action == 'save' ? '/studentCourse/save' : '/studentCourse/apply', 'POST', data, successHandler, failureHandler);
    }

    function accountData()
    {
        return {
                    'studentId' : $('#studentId').val(),
                    'username' : $('#userName').val(),
                    'firstName' : $('#firstName').val(),
                    'lastName' : $('#lastName').val(),
                    'gender' : $('[name=gender]:checked').val(),
                    'nationality' : $('#nationality').val(),
                    'dateOfBirth' : $('#dateOfBirth').val(),
                    'street' : $('#homeAddressStreet').val(),
                    'city' : $('#homeAddressCity').val(),
                    'province' : $('#homeAddressProvince').val(),
                    'country' : $('#homeAddressCountry').val(),
                    'phone' : $('#telephone').val(),
                    'email' : $('#email').val(),
                    'password' : $('#passwordStu').val()
               }
    }

    function ajaxHandler(url, type, data, successHandler, failureHandler)
    {
        var request = {url: url, type: type}
        if(data)
        {
            request['data'] = data
        }
        $.ajax(request)
            .done(
                function(data)
                {
                    if(successHandler)
                    {
                        successHandler(data);
                    }
                }
            ).fail(
                function(xhr, status, error)
                {
                    if(failureHandler)
                    {
                        failureHandler(xhr, status, error);
                    }
                }
            )
    }