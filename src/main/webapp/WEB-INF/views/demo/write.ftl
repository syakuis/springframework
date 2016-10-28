<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=10">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>데모</title>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.serializeJSON/2.7.2/jquery.serializejson.js"></script>
</head>
<body>

<form id="form">
	<p>
		user_id: <input type="text" id="user_id" name="user_id" value="${demo.user_id?if_exists?html}">
	</p>
	<p>
		user_name: <input type="text" id="user_name" name="user_name" value="${demo.user_name?if_exists?html}">
	</p>
	<p>
		user_count: <input type="text" id="user_count" name="user_count" value="${demo.user_count?if_exists?html}">
	</p>

	<p>
		<button type="button" id="save">저장</button>
	</p>
</form>

<script type="application/javascript">
	$("#save").click(function() {
		$.ajax({
			url : '<@spring.url "/demo" />',
			data: JSON.stringify($('#form').serializeJSON()),
			type: 'POST',
			dataType : 'json',
			processData: false,
			contentType: 'application/json'
		}).done(function(body) {
			$('#response').val(body);
		});
	});
</script>

</body>
</html>