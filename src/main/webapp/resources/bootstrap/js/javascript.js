
function changeVideo() 
{
    addVideoToHistory(document.getElementById("videoClip_html5_api").getAttribute("src"));
    var xhr_object = null;
    if (window.XMLHttpRequest) // Firefox 
        xhr_object = new XMLHttpRequest();
    else if (window.ActiveXObject) // Internet Explorer 
        xhr_object = new ActiveXObject("Microsoft.XMLHTTP");
    var request = "php/GetAVideo.php";

    if (path.length > 0)
    {
        console.log("Filters activated.");
        request = request + "?folders=";
        for (var i = 0; i < path.length ; i++)
        {
            request = request + path[i] + ";";
        }
    }
    console.log("request: ", request);
    xhr_object.open("GET", request, false);
    xhr_object.send();
    console.log("response: ", xhr_object.responseText);
    var src = window.location.href + xhr_object.responseText;
    videoPlayer.src({type: "video/webm", src: xhr_object.responseText});
    videoPlayer.currentTime(0);
    videoPlayer.ready(function(){
        var vp = this;
        vp.play();
    })
    processTitle(xhr_object.responseText);
    return false;
};

function changeToPreviousVideo()
{
    if (videosHystory != null && videosHystory.length != 0)
    {
        var previousVideo = videosHystory.pop();
        videoPlayer.src({type: "video/webm", src:  previousVideo});
        videoPlayer.currentTime(0);
        videoPlayer.ready(function(){
            var vp = this;
            vp.play();
        })
        processTitle(previousVideo);
    }
    else
    {
        console.log("This is your first video.. maybe you should TRY THE NEXT ONE !");
    }
}

function addVideoToHistory(videoPath)
{
    if (videoPath != null)
    {
        if (videosHystory == null)
        {
            videosHystory = new Array();
            videosHystory.push(videoPath);
        }
        else
        {
            videosHystory.push(videoPath);
        }
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

function moveVideo(nextFunction)
{
        $("#videoTitle").hide();
        $("#showChooser").hide();
        $("#videoClip").css("width", "320");
        $("#videoClip").css("height", "180");
        $("#videoClip").removeClass("col-xs-offset-2");
        $("#videoMenu").attr("onClick", "showVideo();");
        $("#controlsMenu").attr("onClick", "controlsClickHandler();");
        $("#aboutMenu").attr("onClick", "aboutClickHandler()");
        nextFunction();
}

function aboutClickHandler()
{
    if ((aboutShown === "true") && (controlsShown === "true"))
    {
        aboutShown = "false";
        $("#aboutContent").hide("slide", {direction: "right"}, 400);    
    }
    else if (aboutShown === "false" && controlsShown == "true")
    {
        aboutShown = "true";
        $("#aboutContent").show("slide", {direction: "right"}, 400);
    }
    else if (aboutShown === "false" && controlsShown == "false")
    {
        aboutShown = "true";
        $("#aboutContent").show("slide", {direction: "right"}, 400);
    }
}

function controlsClickHandler()
{
    if ((aboutShown === "true") && (controlsShown === "true"))
    {
        controlsShown = "false";
        $("#controlsContent").hide("slide", {direction: "right"}, 400);    
    }
    else if (controlsShown === "false" && aboutShown == "true")
    {
        controlsShown = "true";
        $("#controlsContent").show("slide", {direction: "right"}, 400);
    }
    else if (controlsShown === "false" && aboutShown == "false")
    {
        controlsShown = "true";
        $("#controlsContent").show("slide", {direction: "right"}, 400);
    }
}

function showVideo()
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
}

function removeVideoPlayerOffset()
{
    console.log($("#videoClip").hasClass("vjs-fullscreen"));
    if ($("#videoClip").hasClass("vjs-fullscreen"))
    {
          $("#videoClip").removeClass("col-xs-offset-2"); 
    }
    else
    {
        $("#videoClip").addClass("col-xs-offset-2");  
    }
}

function updVolumeByTen()
{
    $("#videoClip").volume($("#videoClip").volume() + 0.1);
}

function downVolumeByTen()
{
   $("#videoClip").volume($("#videoClip").volume() - 0.1); 
}