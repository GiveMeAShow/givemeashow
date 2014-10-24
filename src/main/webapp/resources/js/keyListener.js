KEY_LEFT	= 37;
KEY_RIGHT	= 39;

function changeOnKeyDown(_event_)
{
	var myEvent = checkEventObj(_event_);
	var keyCode = myEvent.keyCode;
	if (keyCode == KEY_RIGHT)
	{
		changeVideo();
	}
	else if (keyCode == KEY_LEFT)
	{
		changeToPreviousVideo();
	}
}

function checkEventObj ( _event_ ){
	// --- IE explorer
	if ( window.event )
		return window.event;
	// --- Netscape and other explorers
	else
		return _event_;
}