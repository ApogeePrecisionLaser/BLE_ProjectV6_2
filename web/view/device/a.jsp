<!doctype html>
<html>
    <head>
        <style type="text/css">
            *, *:after, *:before {
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                box-sizing: border-box;
            }


            html, body, div, span, applet, object, iframe,
            h1, h2, h3, h4, h5, h6, p, blockquote, pre,
            a, abbr, acronym, address, big, cite, code,
            del, dfn, em, img, ins, kbd, q, s, samp,
            small, strike, strong, sub, sup, tt, var,
            b, u, i, center,
            dl, dt, dd, ol, ul, li,
            fieldset, form, label, legend,
            table, caption, tbody, tfoot, thead, tr, th, td,
            article, aside, canvas, details, embed,
            figure, figcaption, footer, header, hgroup,
            menu, nav, output, ruby, section, summary,
            time, mark, audio, video {
                margin: 0;
                padding: 0;
                border: 0;
                font-size: 100%;
                font: inherit;
                vertical-align: baseline;
            }
            /* HTML5 display-role reset for older browsers */
            article, aside, details, figcaption, figure,
            footer, header, hgroup, menu, nav, section {
                display: block;
            }
            button,
            input[type="reset"],
            input[type="button"],
            input[type="submit"]
            {
                /* Fix IE7 display bug */
                overflow:visible;
                width:auto;
                border:none;
            }



            ol, ul {
                list-style: none;
            }
            blockquote, q {
                quotes: none;
            }
            blockquote:before, blockquote:after,
            q:before, q:after {
                content: '';
                content: none;
            }
            table {
                border-collapse: collapse;
                border-spacing: 0;
                background:#fff;
                margin:auto;
            }
            body{
                color: #666;
                font-family: Lato, sans-serif, helvetica, arial;
                font-size: 15px;
                line-height: 1;
                background:#f9f9f9
            }

            a {
                color: #36c;
                text-decoration: none;
            }

            a:focus {
                outline: thin dotted;
            }

            a:hover,
            a:active {
                outline: 0;
            }

            a:active,
            a:hover {
                color: #5c85d6;
            }

            h1,
            h2,
            h3,
            h4,
            h5,
            h6 {
                clear: both;
                font-weight: 700;
                margin:0 0 15px 0;
                font-weight:normal;
            }

            h1 {
                font-size: 24px;
                line-height: 1.3846153846;
                color:#000
            }

            h2 {
                font-size: 24px;
                line-height: 1;
                color:#333
            }

            h3 {
                font-size: 20px;
                line-height: 1.0909090909;
                color:#555
            }

            h4 {
                font-size: 16px;
                line-height: 1.2;
            }

            h5 {
                font-size: 18px;
                line-height: 1.3333333333;
            }

            h6 {
                font-size: 16px;
                line-height: 1.5;
            }

            address {
                font-style: italic;
                margin-bottom: 24px;
            }

            abbr[title] {
                border-bottom: 1px dotted #2b2b2b;
                cursor: help;
            }

            b,
            strong {
                font-weight: 700;
            }

            cite,
            dfn,
            em,
            i {
                font-style: italic;
            }

            mark,
            ins {
                background: #fff9c0;
                text-decoration: none;
            }
            p,li{
                line-height:17px;
            }
            p {
                margin-bottom: 15px;
            }

            code,
            kbd,
            tt,
            var,
            samp,
            pre {
                font-family: monospace, serif;
                font-size: 15px;
                -webkit-hyphens: none;
                -moz-hyphens:    none;
                -ms-hyphens:     none;
                hyphens:         none;
                line-height: 1.6;
            }


            pre {
                border: 1px solid rgba(0, 0, 0, 0.1);
                -webkit-box-sizing: border-box;
                -moz-box-sizing:    border-box;
                box-sizing:         border-box;
                margin-bottom: 24px;
                max-width: 100%;
                overflow: auto;
                padding: 12px;
                white-space: pre;
                white-space: pre-wrap;
                word-wrap: break-word;
            }

            blockquote,
            q {
                -webkit-hyphens: none;
                -moz-hyphens:    none;
                -ms-hyphens:     none;
                hyphens:         none;
                quotes: none;
            }

            blockquote:before,
            blockquote:after,
            q:before,
            q:after {
                content: "";
                content: none;
            }

            blockquote {
                color: #767676;
                font-size: 19px;
                font-style: italic;
                font-weight: 300;
                line-height: 1.2631578947;
                margin-bottom: 24px;
            }

            blockquote cite,
            blockquote small {
                color: #2b2b2b;
                font-size: 16px;
                font-weight: 400;
                line-height: 1.5;
            }

            blockquote em,
            blockquote i,
            blockquote cite {
                font-style: normal;
            }

            blockquote strong,
            blockquote b {
                font-weight: 400;
            }

            small {
                font-size: 11px;
                color:#999;
            }
            .weak{
                color:#666
            }
            .strike{
                text-decoration:line-through;
            }
            .relative{
                position:relative;
            }
            big {

                font-size: 125%;
            }

            sup,
            sub {
                font-size: 75%;
                height: 0;
                line-height: 0;
                position: relative;
                vertical-align: baseline;
            }

            sup {
                bottom: 1ex;
            }

            sub {
                top: .5ex;
            }

            dt {
                font-weight: bold;
            }



            ul,
            ol {
                list-style: none;
                margin: 0;
            }

            ul {
                list-style: disc;
            }

            ol {
                list-style: decimal;
            }

            li > ul,
            li > ol {
                margin: 0 0 0 0px;
            }

            img {
                -ms-interpolation-mode: bicubic;
                border: 0;
                vertical-align: middle;
                max-width:100%;
            }

            figure {
                margin: 0;
            }

            fieldset {
                border: 1px solid rgba(0, 0, 0, 0.1);
                margin: 0 0 24px;
                padding: 11px 12px 0;
            }

            legend {
                white-space: normal;
            }

            button,
            input,
            select,
            textarea {
                -webkit-box-sizing: border-box;
                -moz-box-sizing:    border-box;
                box-sizing:         border-box;
                font-size: 100%;
                margin: 0;
                max-width: 100%;
                vertical-align: baseline;
            }

            button,
            input {
                line-height: normal;
            }

            input,
            textarea {
                background-image: -webkit-linear-gradient(hsla(0,0%,100%,0), hsla(0,0%,100%,0)); /* Removing the inner shadow, rounded corners on iOS inputs */
            }

            button,
            html input[type="button"],
            input[type="reset"],
            input[type="submit"] {
                -webkit-appearance: button;
                cursor: pointer;
            }

            button[disabled],
            input[disabled] {
                cursor: default;
            }

            input[type="checkbox"],
            input[type="radio"] {
                padding: 0;
            }

            input[type="search"] {
                -webkit-appearance: textfield;
            }

            input[type="search"]::-webkit-search-decoration {
                -webkit-appearance: none;
            }

            button::-moz-focus-inner,
            input::-moz-focus-inner {
                border: 0;
                padding: 0;
            }

            textarea {
                overflow: auto;
                vertical-align: top;
            }

            table,
            th,
            td {
                border: 1px solid rgba(0, 0, 0, 0.1);
            }

            table {
                border-collapse: separate;
                border-spacing: 0;
                border-width: 1px 0 0 1px;
                margin-bottom: 24px;
                width: 100%;
            }

            caption,
            th,
            td {
                font-weight: normal;
                text-align: left;
                padding:5px 10px;
            }

            th {
                border-width: 0 1px 1px 0;
                font-weight: bold;
            }

            td {
                border-width: 0 1px 1px 0;
            }

            del {
                color: #767676;
            }

            hr {
                background-color: rgba(0, 0, 0, 0.1);
                border: 0;
                height: 1px;
                margin-bottom: 23px;
            }

            body {
                width: 100%;
                min-width: 280px;
                background: #fafafa;
            }

            header {
                background: #fff;
                text-align:center;
            }

            header small{
                display:block;
            }
            .body{
                margin-top:30px;
                margin-bottom:30px;
                min-height:420px;
            }
            footer {
                background: #000;
                color:#fff;
                text-align:center;
                border-top:1px solid #ddd;
                padding:20px 0;
            }
            footer  p{
                margin:0;
            }

            .intro{
                font-size:17px;
                line-height:21px;
                margin-bottom:30px;

            }

            /* custom styles */
            .req{
                font-weight:bold;
            }
            .fLeft{
                float:left;
            }
            .fRight{
                float:right;
            }
            .tRight{
                text-align:right;
            }
            .tCenter{
                text-align:center;
            }

            .grid {
                width: 100%;
                max-width: 940px;
                min-width: 320px;
                margin: 0 auto;
                overflow: hidden;
            }

        </style>
        <script type="text/javascript">
            var myContacts = [
                {"name": "Parvez Ansari", "email": "ansariparvez@gmai.com", "mobile": "9998979695"},
                {"name": "Tayyeb Shaikh", "email": "tshaikh1981@gmai.com", "mobile": "9091929394"},
                {"name": "Ashfaque Shaikh", "email": "ashly786@gmai.com", "mobile": "8081828384"}
            ];

            function generateDynamicTable() {

                var noOfContacts = myContacts.length;

                if (noOfContacts > 0) {


                    // CREATE DYNAMIC TABLE.
                    var table = document.createElement("table");
                    table.style.width = '50%';
                    table.setAttribute('border', '1');
                    table.setAttribute('cellspacing', '0');
                    table.setAttribute('cellpadding', '5');

                    // retrieve column header ('Name', 'Email', and 'Mobile')

                    var col = []; // define an empty array
                    for (var i = 0; i < noOfContacts; i++) {
                        for (var key in myContacts[i]) {
                            if (col.indexOf(key) === -1) {
                                col.push(key);
                            }
                        }
                    }

                    // CREATE TABLE HEAD .
                    var tHead = document.createElement("thead");


                    // CREATE ROW FOR TABLE HEAD .
                    var hRow = document.createElement("tr");

                    // ADD COLUMN HEADER TO ROW OF TABLE HEAD.
                    for (var i = 0; i < col.length; i++) {
                        var th = document.createElement("th");
                        th.innerHTML = col[i];
                        hRow.appendChild(th);
                    }
                    tHead.appendChild(hRow);
                    table.appendChild(tHead);

                    // CREATE TABLE BODY .
                    var tBody = document.createElement("tbody");

                    // ADD COLUMN HEADER TO ROW OF TABLE HEAD.
                    for (var i = 0; i < noOfContacts; i++) {

                        var bRow = document.createElement("tr"); // CREATE ROW FOR EACH RECORD .


                        for (var j = 0; j < col.length; j++) {
                            var td = document.createElement("td");
                            td.innerHTML = myContacts[i][col[j]];
                            bRow.appendChild(td);
                        }
                        tBody.appendChild(bRow)

                    }
                    table.appendChild(tBody);


                    // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
                    var divContainer = document.getElementById("myContacts");
                    divContainer.innerHTML = "";
                    divContainer.appendChild(table);

                }
            }

        </script>
    </head>

    <!-- call getMatrix function On body onload event to get result -->
    <body onload="generateDynamicTable()">
        <header>
            <div class="grid">
                <h1>
                    Web Training Centre
                    <small>Tutorials, Exercises, Assignments</small>
                </h1>
            </div>
        </header>
        <section class="body">
            <div class="grid tCenter">
                <h2>Create a dynamic table using json and javascript</h2>
                <p class="intro">In this tutorial we will show you how to Create a dynamic table using json and javascript without using  jQuery.</p>
                <p><a href="http://www.webtrainingcentre.com/javascript-solutions/create-a-dynamic-table-using-json-and-javascript/">Click here</a> to access the tutorial</p>
            </div>
            <div class="grid tCenter">
                <!-- Div element to display result -->
                <div id="myContacts">
                    <p>You have zero contacts</p>
                </div>
            </div>
        </section>
        <footer>
            <div class="grid">
                <p>
                    Made with Love by <a href="http://www.webtrainingcentre.com">Web Training Centre</a>
                </p>
            </div>
        </footer>


    </body>
</html>

