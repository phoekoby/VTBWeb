<?php

ob_start();
require_once 'index.php';
$text = ob_get_clean();
file_put_contents('index.html', $text);