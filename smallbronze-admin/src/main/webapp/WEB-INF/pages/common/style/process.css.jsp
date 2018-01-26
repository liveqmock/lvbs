<%--
  Created by IntelliJ IDEA.
  User: feifan.gou
  Date: 2017/10/11
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    /* Basic resets */

    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    progress:not(value) {

        /* Add your styles here. As part of this walkthrough we will focus only on determinate progress bars. */

    }

    /* Styling the determinate progress element */

    progress[value] {

        /* Get rid of the default appearance */

        appearance: none;

        /* This unfortunately leaves a trail of border behind in Firefox and Opera. We can remove that by setting the border to none. */

        border: none;

        /* Add dimensions */

        width: 100%;
        height: 20px;

        /* Although firefox doesn't provide any additional pseudo class to style the progress element container, any style applied here works on the container. */

        background-color: whiteSmoke;

        border-radius: 3px;

        box-shadow: 0 2px 3px rgba(0, 0, 0, .5) inset;

        /* Of all IE, only IE10 supports progress element that too partially. It only allows to change the background-color of the progress value using the 'color' attribute. */

        color: royalblue;

        position: relative;

    }
    progress[value]::-webkit-progress-bar {

        background-color: whiteSmoke;

        border-radius: 3px;

        box-shadow: 0 2px 3px rgba(0, 0, 0, .5) inset;

    }

    progress[value]::-webkit-progress-value {

        position: relative;

        background-size: 35px 20px, 100% 100%, 100% 100%;

        border-radius: 3px;

        /* Let's animate this */

        animation: animate-stripes 5s linear infinite;

    }

    @keyframes animate-stripes {
        100% {
            background-position: -100px 0;
        }
    }

    /* Let's spice up things little bit by using pseudo elements. */

    progress[value]::-webkit-progress-value:after {

        /* Only webkit/blink browsers understand pseudo elements on pseudo classes. A rare phenomenon! */

        content: '';

        position: absolute;

        width: 5px;
        height: 5px;

        top: 7px;
        right: 7px;

        background-color: white;

        border-radius: 100%;

    }

    /* Firefox provides a single pseudo class to style the progress element value and not for container. -moz-progress-bar */

    progress[value]::-moz-progress-bar {

        /* Gradient background with Stripes */

        background-image: -moz-linear-gradient(135deg,
        transparent,
        transparent 33%,
        rgba(0, 0, 0, .1) 33%,
        rgba(0, 0, 0, .1) 66%,
        transparent 66%),
        -moz-linear-gradient(top,
                rgba(255, 255, 255, .25),
                rgba(0, 0, 0, .2)),
        -moz-linear-gradient(left, #09c, #f44);

        background-size: 35px 20px, 100% 100%, 100% 100%;

        border-radius: 3px;
    }

    .progress-bar {

        background-color: whiteSmoke;

        border-radius: 3px;

        box-shadow: 0 2px 3px rgba(0, 0, 0, .5) inset;

        /* Dimensions should be similar to the parent progress element. */

        width: 100%;
        height: 20px;

    }

    .progress-bar span {

        background-color: royalblue;

        border-radius: 3px;

        display: block;

        text-indent: -9999px;

    }

    p[data-value] {

        position: relative;

    }

    /* The percentage will automatically fall in place as soon as we make the width fluid. Now making widths fluid. */

    p[data-value]:after {

        content: attr(data-value) '%';

        position: absolute;
        right: 0;

    }

    .css3::-webkit-progress-value{

        /* Gradient background with Stripes */

        background-image: -webkit-linear-gradient(135deg,
        transparent,
        transparent 33%,
        rgba(0, 0, 0, .1) 33%,
        rgba(0, 0, 0, .1) 66%,
        transparent 66%),
        -webkit-linear-gradient(top,
                rgba(255, 255, 255, .25),
                rgba(0, 0, 0, .2)),
        -webkit-linear-gradient(left, #09c, #ff0);

    }
    .css3::-moz-progress-bar{

        background-image: -moz-linear-gradient(135deg,
        transparent,
        transparent 33%,
        rgba(0, 0, 0, .1) 33%,
        rgba(0, 0, 0, .1) 66%,
        transparent 66%),
        -moz-linear-gradient(top,
                rgba(255, 255, 255, .25),
                rgba(0, 0, 0, .2)),
        -moz-linear-gradient(left, #09c, #ff0);

    }
    progress {
        z-index: 1000;
    }
</style>