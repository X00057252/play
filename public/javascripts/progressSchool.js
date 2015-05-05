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

        
        if($('#selectedCourseId').val()) // setting the value to course drop-down based on course we selected in previous page - apply.scala.html
        {
            $('#courseSelect').val($('#selectedCourseId').val())
        }
        $('#studentCourseForm').submit(function(event) { //assigning an anonymous function to submit event of the form studentCourseForm
            event.preventDefault(); //stops the default action of an element from happening

            //check if confirm email and confirm password match with originals and submit apply course
            if($('#email').val() != $('#emailConf').val())
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

        //check if confirm email and confirm password match with originals and submit update profile on account.scala.html
        $('#accountUpdateForm').submit(function(event) { //assigning an anonymous function to submit event of the form accountUpdateForm
            event.preventDefault(); //stops the default action of an element from happening
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
        var successHandler = function(){ $('#message').text('Account update is successful').show().delay(5000).fadeOut();} //display success message
        var failureHandler = function(xhr, status, error){ $('#message').text('Course Save is failed with ' + error).show().delay(5000).fadeOut();} //display failure message with error code
        ajaxHandler('updateProfile', 'POST', data, successHandler, failureHandler);
    }

    //function for booking/unbooking event
    function submitEvent(eventId, studentId){
        var action = $('#action'+eventId).val(); // select book or unbook action from account.scala.html
        var url = (action == 'book' ? ('/bookEvent/student/'+studentId+'/event/'+eventId) : ('/unBookEvent/student/'+studentId+'/event/'+eventId)); // set url based on action type
        var successHandler = function(data){
                                $('#message1').text('Action successful').show().delay(5000).fadeOut();
                                $('#action'+eventId).val(action == 'book' ? 'unbook' : 'book');
                                $('#eventSubmitBtn'+eventId).text(action == 'book' ? 'No, I won\'t be there.' : 'Yes, I\'m going!'); //re-label event button
                                $('#currentCapacity'+eventId).html($.parseJSON(data)['freeSpace']); //adjust currentCapacity
                             }
                            
        var failureHandler = function(xhr, status, error){
         $('#message1').text('Action failed with error ' + xhr.responseText).show().delay(5000).fadeOut();
        }
        ajaxHandler(url, 'POST', null, successHandler, failureHandler);
    }

	//function for applying for a course
    function applyCourse(action)
    {
        var data = accountData();
        data['courseId'] = $('#courseSelect').val(); // setting the value to course drop-down based on course we selected  - apply.scala.html
        data['courseStartDate'] = $('#courseStartDate').val();
        data['accomodationType'] = $('[name=accommOption]:checked').val();
        var successHandler = function(){ $('#message').text('Course Save is successful').show().delay(5000).fadeOut();
                                        if(action == 'save')
                                        {
                                            window.location="/account" // open /account page if successful
                                        }
                                        else
                                        {
                                            window.location="/paymentgpg?courseId="+data['courseId'] //open paymentgpg with course details
                                        }
                                        }
        var failureHandler = function(xhr, status, error){ $('#message').text('Course Save is failed with ' + error).show().delay(5000).fadeOut();}
        ajaxHandler(action == 'save' ? '/studentCourse/save' : '/studentCourse/apply', 'POST', data, successHandler, failureHandler);
    }
	// function for populating the form with student details at apply.scala.html
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

    //ajax handler which takes url, verb data, callback methods and make ajax calls
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