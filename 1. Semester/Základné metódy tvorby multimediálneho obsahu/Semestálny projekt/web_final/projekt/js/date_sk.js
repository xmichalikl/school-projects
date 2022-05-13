var date = new Date(), 
	dayStr, 
	month;

switch( date.getDay() - 1 ){
	case 0: dayStr = "Po"; break; 
	case 1: dayStr = "Ut"; break;
	case 2: dayStr = "St"; break;
	case 3: dayStr = "Št"; break;
	case 4: dayStr = "Pi"; break;
	case 5: dayStr = "So"; break;
	case 6: dayStr = "Ne"; break;
}

switch( date.getMonth() ){
	case 0: month  = "január"; break;
	case 1: month  = "febebruár"; break;
	case 2: month  = "marec"; break;
	case 3: month  = "apríl"; break;
	case 4: month  = "máj"; break;
	case 5: month  = "jún"; break;
	case 6: month  = "júl"; break;
	case 7: month  = "august"; break;
	case 8: month  = "september"; break;
	case 9: month  = "október"; break;
	case 10: month = "november"; break;
	case 11: month = "december"; break;
}

var dayNum  = date.getDate(),
	year    = date.getFullYear();

var dateStr = dayStr + " " + dayNum +". "+ month +" "+ year;

document.querySelector(".info-bar p").innerHTML = dateStr;
