<?php

global $script_queue, $style_queue;
$script_queue = [];
$style_queue = [];

require_once 'functions.php';

?>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<!--    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>-->
</head>
<body>

<div id="App" class="container-fluid"></div>
<?php loadFile('template/load') ?>
<?php load('pages/App') ?>


<script type="text/javascript" src="/assets/index.js"></script>
<script type="text/javascript" src="/assets/Component.js"></script>
<script type="text/javascript" src="/assets/api.js"></script>
<script type="text/javascript" src="/assets/Auth.js"></script>
<script type="text/javascript" src="/assets/User.js"></script>

<?php foreach($script_queue as $script) : ?>
<script type="text/javascript"><?= $script ?></script>
<?php endforeach; ?>

<style>
    <?php echo implode(PHP_EOL, $style_queue); ?>
</style>
</body>
</html>


