<?php
	if($_SERVER['REQUEST_METHOD']=='POST') {
		$image = $_POST['image'];
		$name = $_POST['name'];
	 	require_once "connect.php";
	 	$sql1 = "SELECT LoanID FROM Loans ORDER BY LoanID ASC";
	 	$res = mysqli_query($conn, $sql1);
		$id = 0;
		while($row = mysqli_fetch_array($res)) {
			$id = $row['id'];
		}
		$path = "uploads/$id.png";
		$actualpath = "http://simplifiedcoding.16mb.com/PhotoUploadWithText/$path";
		$sql2 = "INSERT INTO Loans (photo, name) VALUES ('$actualpath','$name')";
		if(mysqli_query($conn, $sql)) {
			file_put_contents($path, base64_decode($image));
			echo "Successfully Uploaded";
		}
		mysqli_close($conn);
	}
?>