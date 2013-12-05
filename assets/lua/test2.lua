
function getPhoneNumber()
	return text..context:getTelephonyManager():getLine1Number();
end
