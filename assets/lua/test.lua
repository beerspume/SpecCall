text='Phone number is ';
temp="AAAAAAAAAAAA";

setContext=function(c)
	context=c;
end

function add(a,b)
	return a+b;
end

sub=function(a,b)
	return a-b;
end

setText=function(v)
	text=v;
end


runCallback=function()
	context:runCallback(callbackFunc);
end


callbackFunc=function(s)
	setText(s);
end

testSimInfo=function()
	local tm=context:getTelephonyManager();
	context:Log_i("test.lua","Phone number:"..tm:getLine1Number());
end
