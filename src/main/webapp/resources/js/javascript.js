
function decreaseIndex()
{
	if (index > 0)
	{
		index = index - 1;
	}
	else
	{
		index = 0;
	}
}



function managePath(folderName, id) 
{
    //console.log("entering managePath");
    var index = path.indexOf(folderName);
    if (index != -1)
    {
        path.splice(index, 1);
        document.getElementById(id).setAttribute("class", "showBox_hidden");
    }
    else
    {
        path.push(folderName);
        document.getElementById(id).setAttribute("class", "showBox_visible");
    }
}

function processTitle(fullpath)
{
    var fullVideoName = fullpath.substring(fullpath.lastIndexOf("/") + 1);
    fullVideoName = fullVideoName.replace(".webm", "");
    fullVideoName = fullVideoName.replace("VOSTFR", "");
    fullVideoName = fullVideoName.split("_").join(" ");
    document.getElementById("videoTitle").innerHTML = fullVideoName;
}


/*function showVideo()
{
    aboutShown = "false";
    controlsShown = "false";
    $("#aboutMenu").attr("onClick", "moveVideo(aboutClickHandler);");
    $("#controlsMenu").attr("onClick", "moveVideo(controlsClickHandler);");
    $("#controlsContent").hide(400);
    $("#aboutContent").hide(400);
    
    $("#videoClip").css("width", "640");
    $("#videoClip").css("height", "360");
    $("#videoClip").addClass("col-xs-offset-2");
    $(".mainContent_side").switchClass("mainContent_side", "mainContent");
    $(".videoContainer_sided").switchClass("videoContainer_sided", "videoContainer");
    
    $(".video_playlist_side").switchClass("video_playlist_side", "video_playlist").show("fade", 400);
    $("#showChooser").show("fade", 400);
}*/



function updVolumeByTen()
{
    $("#videoClip").volume($("#videoClip").volume() + 0.1);
}

function downVolumeByTen()
{
   $("#videoClip").volume($("#videoClip").volume() - 0.1); 
}