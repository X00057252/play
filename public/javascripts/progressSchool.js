


//===========================================================
//
//		IT Tallaght, Bart Bula, X00107883, March 2015 
//
//=========================================================== 





// Governing read more/less links in articles: --
// 		shID+'show' - "Read more" link
//		shID - Hidden section            

function showHide(shID) {
	var top = document.getElementById(shID+'-top').offsetTop; //Getting Y of top of article
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






// Opens a modal pop up window: --

function popUp() { 
    this.confBox = null;   // confBox - confirmation message box
    this.confBack = null;  // confBack - transparent grey background 

	// this creates pop-up window message elements and opens pop up:
    this.popOut = function() {
        //create grey transparent background:
        this.confBack = document.createElement("div");
        this.confBack.className = "confBack";
		// create message box:
        this.confBox = document.createElement("div");
        this.confBox.className = "confBox";
        this.confBox.Code = this;
		//create message itself:
        var msg = document.createElement("article");
        msg.className = "confMsg";
        msg.innerHTML = 'Thank you!<br>The form has been submitted.<br><br>Click "Close" to go back to home page.';
        this.confBox.appendChild(msg);
        //create button to close the pop-up:
		var closebtn = document.createElement("button");
		closebtn.className="popBttn";
        closebtn.onclick = function() { 
            this.parentNode.Code.popIn();
        }
        closebtn.innerHTML = "Close";
        this.confBox.appendChild(closebtn);

        document.body.appendChild(this.confBack);
        document.body.appendChild(this.confBox);
    }


	// this closes pop up window: --
    this.popIn = function() {
        // If confBox is displayed (window open) - close it
		if (this.confBox != null) {
            document.body.removeChild(this.confBox);
            this.confBox = null;
        }
		// If confBack is displayed (window open) - close it
        if (this.confBack != null) {
			document.body.removeChild(this.confBack);
        this.confBack = null;
        }
		location.href = "index.html";
	} 
}
// End of: opens a modal pop up window --







//creates new popUp window object:

function callPop(){
	var pop = new popUp();
    pop.popOut();
	return false;
}
// End of: creates new popUp window --






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




// Change size of the font: --

function resizeFont(newSize) {
  if (document.body.style.fontSize == "") {
    document.body.style.fontSize = "1.0em";
  }
  document.body.style.fontSize = parseFloat(newSize) + "em";
}
// End of: Change size of the font --





