var date = new Date(), 
	dayStr, 
	month;

switch( date.getDay() ){
	case 0: dayStr = "Monday"; break;
	case 1: dayStr = "Tuesday"; break;
	case 2: dayStr = "Wednesday"; break;
	case 3: dayStr = "Thursday"; break;
	case 4: dayStr = "Friday"; break;
	case 5: dayStr = "Saturday"; break;
	case 6: dayStr = "Sunday"; break;
}

switch( date.getMonth() ){
	case 0: month  = "January"; break;
	case 1: month  = "Febebruary"; break;
	case 2: month  = "March"; break;
	case 3: month  = "April"; break;
	case 4: month  = "May"; break;
	case 5: month  = "June"; break;
	case 6: month  = "July"; break;
	case 7: month  = "August"; break;
	case 8: month  = "September"; break;
	case 9: month  = "October"; break;
	case 10: month = "November"; break;
	case 11: month = "December"; break;
}

var dayNum  = date.getDate(),
	year    = date.getFullYear(),
	hours   = ( date.getHours() > 9 ) ? date.getHours() : "0"+ date.getHours();
	minutes = ( date.getMinutes() > 9 ) ? date.getMinutes() : "0"+ date.getMinutes();

var dateStr = dayNum +". "+ month +" "+ year;

document.querySelector(".info-bar p").innerHTML = dateStr;
