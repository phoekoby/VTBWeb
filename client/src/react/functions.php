<?php

function extractScripts(string $html): string
{
    global $script_queue;
    $pattern = "/<script>(.*)<\/script>/s";
    $scripts = [];
    preg_match_all($pattern, $html, $matches);
    foreach($matches[1] as $match){
        $scripts[] = $match;
    }
    $script_queue = array_merge($script_queue, $scripts);
    return preg_replace($pattern, '', $html);
}

function extractStyles(string $html): string
{
    global $style_queue;
    $pattern = "/<style>(.*)<\/style>/s";
    $styles = [];
    preg_match_all($pattern, $html, $matches);
    foreach($matches[1] as $match){
        $styles[] = $match;
    }
    $style_queue = array_merge($style_queue, $styles);
    return preg_replace($pattern, '', $html);
}

function loadFile(string $name){
    ob_start();
    require $_SERVER['DOCUMENT_ROOT'] . "/$name.php";
    $text = ob_get_clean();
    $text = extractScripts($text);
    $text = extractStyles($text);
    echo $text;
}

function load(string $name){
    echo '<script type="text/html" template="'.$name.'">';
    loadFile("template/$name");
    echo '</script>';
}


