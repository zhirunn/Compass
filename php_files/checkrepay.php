<?php
	if($_SERVER['REQUEST_METHOD']=='POST') {
		$id = $_POST['id'];
		require_once "connect.php";
		$sql = "SELECT Status FROM Loans WHERE LoanID = '$id'";
		$res = mysqli_query($conn, $sql);
        $row = mysqli_fetch_array($res);
        $return = $row['Status'];
        ob_clean();
        echo $return;
        mysqli_close($conn);
	}
?>