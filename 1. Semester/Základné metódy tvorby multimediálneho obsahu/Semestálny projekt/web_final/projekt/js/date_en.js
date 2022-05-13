var date = new Date(), 
	dayStr, 
	month;

switch( date.getDay() - 1 ){
	case 0: dayStr = "Mon"; break;
	case 1: dayStr = "Tue"; break;
	case 2: dayStr = "Wen"; break;
	case 3: dayStr = "Thu"; break;
	case 4: dayStr = "Fri"; break;
	case 5: dayStr = "Sat"; break;
	case 6: dayStr = "Sun"; break;
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
	year    = date.getFullYear();

var dateStr = dayStr +" "+ dayNum +". "+ month +" "+ year;

document.querySelector(".info-bar p").innerHTML = dateStr;
