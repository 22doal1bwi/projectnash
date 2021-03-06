<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>Nash Inc. Intranet</title>
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="../css_custom/intranet_style.css" rel="stylesheet">
    <link rel="shortcut icon" href="../img/nash/nash_favicon.ico" />
	</head>
	<body>
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">
        <a class="navbar-brand" rel="home" href="../index.html"><img src="../img/nash/nash_logo_text_50x185_white_bkg.png" alt="favicon" class="nash_logo"/></a>
		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		<span class="sr-only">Toggle navigation</span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
		</button>
	</div>
	<div class="collapse navbar-collapse">
		<ul class="nav navbar-nav">
			<li><a href="../index.html">Start</a></li>
			<li><a href="#">Meine Arbeit</a></li>
			<li><a href="#">Mitarbeiter</a></li>
			<li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Services <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Urlaubsbeantragung</a></li>
                <li><a href="zeiterfassung.php">Zeiterfassung</a></li>
                <li><a href="../certificates/home.jsp">Zertifikatsverwaltung</a></li>
                <li><a href="#">Fahrtenbücher</a></li>
              </ul>
            </li>
		</ul>
		<div class="col-sm-3 col-md-3 pull-right">
          <form class="navbar-form" role="search">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Suchen" name="srch-term" id="srch-term">
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                </div>
            </div>
          </form>
		</div>
	</div>
</nav>

<div class="container-fluid">
  
  <!--left-->
  <div class="col-sm-3">
        <h2>Termine</h2>
    	<div class="panel panel-default">
         	<div class="panel-heading">Besuch von Steve Nash</div>
         	<div class="panel-body">Unternehmensleiter Steve Nash wird am <b>19. Juni</b> die Zentrale besuchen und einen kurzen
          Vortrag über aktuelle Themen halten.</div>
        </div>
        <hr>
        <div class="panel panel-default">
          <div class="panel-heading">Sommerfest 2015</div>
          <div class="panel-body">Am <b>13. Juli</b> findet, wie jedes Jahr, das Sommerfest der Nash Inc. statt! Bitte geben Sie
          rechtzeitig Bescheid mit wie vielen Personen Sie teilnehmen.</div>
        </div>
        <hr>
        <div class="panel panel-default">
          <div class="panel-heading">Launch der neuen Werbekampagne</div>
          <div class="panel-body">Anfang August wird unser neuer Werbespot "Never gonna give you up" vorgestellt.
          Die Kampagne wird unsere Kunden nicht enttäuschen!</div>
        </div>
        <hr>
        <div class="panel panel-default">
         	<div class="panel-heading">Nash Inc. Spendenlauf</div>
         	<div class="panel-body">Bitte melden Sie sich an, wenn Sie auch dieses Jahr wieder am Nash Inc. Spendenlauf
          teilnehmen werden. Sämtliche erlöse werden gespendet.</div>
        </div>
        <hr>
  </div><!--/left-->
  
  <!--center-->
  <div class="col-sm-6">
    <div class="row">
      <div class="col-xs-12">
        <h2>Zeiterfassung von <?= $_SERVER[SSL_CLIENT_S_DN_CN] ?></h2>

        <p>Hier sehen Sie Ihre Arbeitszeiten des aktuellen Monats. Bei Fragen oder Anmerkungen
        bitte an die Durchwahl -66821 wenden.</p>

        <h4>Aktueller Monat: Juni</h4>
        <p>Nachtrag aus dem Vormonat: <b>+14:37</b></p>
        <div class="col-md-12">
          <table class="table table-striped text-center">
            <thead>
              <tr>
                <th style="text-align: center;">Tag</th>
                <th style="text-align: center;">Kommt</th>
                <th style="text-align: center;">Geht</th>
                <th style="text-align: center;">Anwesenheit</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1.</td>
                <td>7:34</td>
                <td>17:12</td>
                <td>8:38</td>
              </tr>
              <tr>
                <td>2.</td>
                <td>7:31</td>
                <td>17:41</td>
                <td>9:10</td>
              </tr>
              <tr>
                <td>3.</td>
                <td>7:39</td>
                <td>17:56</td>
                <td>9:17</td>
              </tr>
              <tr>
                <td>4.</td>
                <td></td>
                <td></td>
                <td>Feiertag</td>
              </tr>
              <tr>
                <td>5.</td>
                <td></td>
                <td></td>
                <td>Gleittag</td>
              </tr>
              <tr>
                <td>6.</td>
                <td>--</td>
                <td>--</td>
                <td>--</td>
              </tr>
              <tr>
                <td>7.</td>
                <td>--</td>
                <td>--</td>
                <td>--</td>
              </tr>
              <tr>
                <td>8.</td>
                <td>7:28</td>
                <td>16:12</td>
                <td>8:24</td>
              </tr>
              <tr>
                <td>9.</td>
                <td>8:04</td>
                <td>16:07</td>
                <td>8:44</td>
              </tr>
              <tr>
                <td>10.</td>
                <td>7:44</td>
                <td>18:22</td>
                <td>9:04</td>
              </tr>
              <tr>
                <td>11.</td>
                <td>7:40</td>
                <td>16:58</td>
                <td>8:13</td>
              </tr>
              <tr>
                <td>12.</td>
                <td>7:34</td>
                <td>17:51</td>
                <td>9:27</td>
              </tr>
              <tr>
                <td>13.</td>
                <td>--</td>
                <td>--</td>
                <td>--</td>
              </tr>
              <tr>
                <td>14.</td>
                <td>--</td>
                <td>--</td>
                <td>--</td>
              </tr>
              <tr>
                <td>15.</td>
                <td>7:54</td>
                <td>17:13</td>
                <td>9:41</td>
              </tr>
              <tr>
                <td>16.</td>
                <td>7:36</td>
                <td>16:00</td>
                <td>8:51</td>
              </tr>
              <tr>
                <td>17.</td>
                <td>7:34</td>
                <td>17:19</td>
                <td>9:07</td>
              </tr>
              <tr>
                <td>18.</td>
                <td>8:19</td>
                <td>18:21</td>
                <td>8:53</td>
              </tr>
              <tr>
                <td>19.</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td>20.</td>
                <td>--</td>
                <td>--</td>
                <td>--</td>
              </tr>
              <tr>
                <td>21.</td>
                <td>--</td>
                <td>--</td>
                <td>--</td>
              </tr>
              <tr>
                <td>22.</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td>23.</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td>24.</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td>25.</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td>26.</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td>27.</td>
                <td>--</td>
                <td>--</td>
                <td>--</td>
              </tr>
              <tr>
                <td>28.</td>
                <td>--</td>
                <td>--</td>
                <td>--</td>
              </tr>
              <tr>
                <td>29.</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              <tr>
                <td>30.</td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

        <p class="lead"><button class="btn btn-default">Zeit-Archiv</button></p>
        <p class="pull-right"><span class="label label-default">Zeiterfassung</span> <span class="label label-default">HR</span> <span class="label label-default">Mitarbeiter</span></p>
        <!--<ul class="list-inline"><li><a href="#">Vor 2 Tagen</a></li><li><a href="#"><i class="glyphicon glyphicon-comment"></i> 4 Kommentare</a></li></ul>-->
      </div>
    </div>
    <hr>
  <!--</div>--> <!--/center-->

  <!--right-->
  <div class="col-sm-3">
        <h2>Informationen</h2>
    	<div class="panel panel-default">
         	<div class="panel-heading">Intranet-Links</div>
         	<div class="panel-body">
            <ol class="list-unstyled">
              <li><a href="#">Entgeltabrechnung</a></li>
              <li><a href="#">Zeitverwaltung</a></li>
              <li><a href="#">Persönliche Informationen</a></li>
              <li><a href="#">Intranet Chat</a></li>
              <li><a href="#">Gehaltsentwicklung</a></li>
            </ol>
          </div>
        </div>
        <hr>
        <div class="panel panel-default">
         	<div class="panel-heading">Problembehebung</div>
         	<div class="panel-body">
            <ol class="list-unstyled">
              <li><a href="#">Helpdesk</a></li>
              <li><a href="#">Pforte</a></li>
              <li><a href="#">Sicherheitsdienst</a></li>
            </ol>
          </div>
        </div>
        <hr>
        <div class="panel panel-default">
         	<div class="panel-heading">News Archiv</div>
         	<div class="panel-body">
            <ol class="list-unstyled">
              <li><a href="#">Juni 2015</a></li>
              <li><a href="#">Mai 2015</a></li>
              <li><a href="#">April 2015</a></li>
              <li><a href="#">März 2015</a></li>
              <li><a href="#">Februar 2015</a></li>
              <li><a href="#">januar 2015</a></li>
              <li><a href="#">Dezember 2014</a></li>
              <li><a href="#">November 2014</a></li>
              <li><a href="#">Oktober 2014</a></li>
              <li><a href="#">September 2014</a></li>
              <li><a href="#">August 2014</a></li>
              <li><a href="#">Juli 2014</a></li>
            </ol>
          </div>
        </div>
        <hr>
  </div><!--/right-->
  <hr>
</div> <!--/container-fluid-->
	<!-- script references -->
		<script src="../bower_components/jquery/dist/jquery.min.js"></script>
		<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	</body>
</html>