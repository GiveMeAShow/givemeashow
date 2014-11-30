
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


function updVolumeByTen()
{
    $("#videoClip").volume($("#videoClip").volume() + 0.1);
}

function downVolumeByTen()
{
   $("#videoClip").volume($("#videoClip").volume() - 0.1); 
}