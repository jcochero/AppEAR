<?php

$action=$_GET["Action"];

switch ($action) {
    Case "M":
        $r=$_GET["r"];
        $t=$_GET["t"];
        $m=$_GET["m"];
       
        $j=json_decode(notify($r, $t, $m));
       
        $succ=0;
        $fail=0;
       
        $succ=$j->{'success'};
        $fail=$j->{'failure'};
       
        print "Success: " . $succ . "<br>";
        print "Fail   : " . $fail . "<br>";
       
        break;
default:
        print json_encode ("Error: Function not defined ->" . $action);
}

function notify ($r, $t, $m)
    {
    // API access key from Google API's Console
        if (!defined('API_ACCESS_KEY')) define( 'API_ACCESS_KEY', 'AAAAHGCbv2o:APA91bHVJnhsmC4Pu1UEKn-xzRBfQYnwV-YYWxoz3BGYV1NN-d6mc97c5kgIFsg3wQ2lFqQKz1_e0CSt9TyaLhTPj-d0OmMR2U29gS-8yBvS5SQSBFu7dWKmgtnKxpiBK9KhQ9wlDfTE' );
        $tokenarray = array($r);
        // prep the bundle
        $msg = array
        (
            'title'     => $t,
            'body'     => $m,
            'MyKey1'       => 'MyData1',
            'MyKey2'       => 'MyData2',
           
        );
        $fields = array
        (
            'registration_ids'     => $tokenarray,
            'data'            => $msg,
        );
        
        $headers = array
        (
            'Authorization: key=' . API_ACCESS_KEY,
            'Content-Type: application/json',
        );
        
        $ch = curl_init();
        curl_setopt( $ch,CURLOPT_URL, 'fcm.googleapis.com/fcm/send' );
        curl_setopt( $ch,CURLOPT_POST, true );
        curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
        curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
        curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
        curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
        $result = curl_exec($ch );
        curl_close( $ch );
        return $result;
    }
?>