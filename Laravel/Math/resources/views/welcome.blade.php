<!DOCTYPE html>
<html lang="en">
   <head>
      <meta charset="utf-8">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title> MATH CHALLENGE  </title>
      <!-- Favicon -->
      <link rel="icon" href="{{asset('assets2/images/favicon.png')}}" type="image/x-icon" />
      <!-- Bootstrap CSS -->
      <link href="{{asset('assets2/css/bootstrap.min.css')}}" rel="stylesheet">
      <!-- Animate CSS -->
      <link href="{{asset('assets2/vendors/animate/animate.css')}}" rel="stylesheet">
      <!-- Icon CSS-->
      <link rel="stylesheet" href="{{asset('assets2/vendors/font-awesome/css/font-awesome.min.css')}}">
      <!-- Camera Slider -->
      <link rel="stylesheet" href="{{asset('assets2/vendors/camera-slider/camera.css')}}">
      <!-- Owlcarousel CSS-->
      <link rel="stylesheet" type="text/css" href="{{asset('assets2/vendors/owl_carousel/owl.carousel.css')}}" media="all">
      <!--Template Styles CSS-->
      <link rel="stylesheet" type="text/css" href="{{asset('assets2/css/style.css')}}" media="all" />
      <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,400i,600,600i,700,700i,800,800i&amp;subset=cyrillic,cyrillic-ext,greek,greek-ext,latin-ext,vietnamese" rel="stylesheet">
   </head>
   <body id="top">
      <div class="bg-grediunt">
         <div class="bg-banner-img1">
            <div class="overlay-all ">
               <!-- Header_Area -->
               <!-- header
                  ================================================== -->
               <header class="s-header">
                  <div class="header-logo">
                     <a class="navbar-brand logo-biss" href="index.html">  </a>
                  </div>
                  <!-- end header-logo -->
                  <nav class="header-nav">
                     <a href="#0" class="header-nav__close" title="close"><span>Close</span></a>
                     <div class="header-nav__content">
                        <h3>MATHEMATICS CHALLENGE </h3>
                        <ul class="header-nav__list">
                           <li class="current"><a class=""  href="index.html" >Home</a></li>
                           <li><a class=""  href="about.html" >About</a></li>
                          @if (Route::has('login'))
                           <li><a class=""  href="contact.html">Contact</a></li>
                    @auth
                    <li><a href="{{ url('/dashboard') }}" class="">Dashboard</a></li>
                    @else
                    <li><a href="{{ route('login') }}" class="">Log in</a></li>
                   @if (Route::has('register'))
                   <li><a href="{{ route('register') }}" class="">Register</a></li>
                        @endif
                    @endauth
                    @endif
                        </ul>
                        <ul class="header-nav__social">
                           <li>
                              <a href="" target="_blank"><i  class="fa fa-facebook-square fa-3x social"></i></a>
                           </li>
                           <li>
                              <a href="" target="_blank"><i  class="fa fa-twitter-square fa-3x social"></i></a>
                           </li>
                           <li>
                              <a href="" target="_blank"><i  class="fa fa-instagram fa-3x social"></i></a>
                           </li>
                           <li>
                              <a href="#0"><i class="fa fa-linkedin-square fa-3x social"></i></a>
                           </li>

                        </ul>
                     </div>
                     <!-- end header-nav__content -->
                  </nav>
                  <!-- end header-nav -->
                  <a class="header-menu-toggle" href="#0">
                  <span class="header-menu-icon"></span>
                  </a>
               </header>
               <!-- end s-header -->
               <!-- End Header_Area -->
               <!-- #banner start -->
               <section id="banner" class=" mb-90">
                  <div class="container banner-img1.jpg">
                     <div class="row">
                        <!-- #banner-text start -->
                        <div id="banner-text" class="col-md-7 text-c text-left ">
                           <h5 class="wow fadeInUp main-h" data-wow-delay="0.2s" >INTERNATIONAL EDUCATION SERVICES<br> Mathematics Challenges</h5>
                           <p class="banner-text wow fadeInUp main-h3" data-wow-delay="0.8s">Unleash your inner genuis with our exilarating math challenge <br> designed to test and expand your problem-solving skills <br>need to. </p>
                        </div>
                        <!-- /#banner-text End -->
                     </div>
                  </div>
               </section>
               <div class="container-fluid p0 banner-shap-img">
               </div>
            </div>
         </div>
         <!-- /#banner end -->
         <!--#Our banner-shap- Area -->
         <!--#EndOur banner-shap- Area -->
         <!-- #About Us Area start -->
         <div  id="about"  class=" py-70 pb_90">
            <div class="container About-right-bg-img ">
               <div class="row text-left  ">
                  <div class="col-md-7">
                     <div class="about_left_text wow fadeInUp">
                        <h1>ABOUT US     </h1>
                        <p>Math is the only language spoken worldwide, transcending <br> borders and cultures. </p>
                        <p>Tackling mathematical problems enhances your analytical thinking and problem-solving abilities.Solving math problems keeps your brain active and healthy, much like physical exercise keeps your body fit.Math is the backbone of technological advancements, driving innovation in fields like engineering, computer science, and economics.</p>
                     </div>
                  </div>
                  <div class="col-md-5">
                  </div>
               </div>
            </div>
         </div>
         <!-- #About Us Area End -->
         <!--#Our Partners Area -->
        
      <!--#start Our footer Area -->
      <div class="our_footer_area">
         <div class="book_now_aera ">
            <div class="container wow fadeInUp">
               <div class="row book_now">
                  <div class="col-md-4">
                     <div class="">
                        <a class=" logo-biss" href="index.html">  <img src="assets/images/logo.png"></a>
                     </div>
                     <div class="bigpixi-footer-social">
                        <a href="" target="_blank" class="ml-15"><i id="social-fb" class="fa fa-facebook fa-2x social"></i></a>
                        <a href="" target="_blank" class="ml-15"><i id="social-tw" class="fa fa-twitter fa-2x social"></i></a>
                        <a href="" target="_blank" class="ml-15"><i id="social-em" class="fa fa-instagram fa-2x social"></i></a>
                        <a href="" target="_blank" class="ml-15"><i id="social-em" class="fa fa-linkedin fa-2x social"></i></a>
                     </div>
                     <p class="footer-h"><a href=""> enquiry@IES.com </a></p>
                     <p class="footer-h">+256-772-001-223  </p>
                  </div>
                  <div class="col-md-1 ">
                  </div>
                  <div class="col-md-4">
                     <ul class="location">
                        <li class="footer-left-h"><i class="fa fa-map-marker"></i>Address<br>Makerere Uni, Cocis, <br>Kampala, UGANDA</li>
                        <li class="footer-left-h"><i class="fa fa-phone"></i>Call Us <br>+256-772-001-223 
                           <br>+256-772-001-223 
                        </li>
                        <li class="footer-left-h"><i class="fa fa-envelope-o"></i>Email<br>
                           <a href=""> enquiry@IES.com </a></br><a href=""> enquiry@mathchallenge.com </a>
                        </li>
                     </ul>
                  </div>
                  <div class="col-md-12">
                     <p class="color-gray"> DESIGNED BY GROUP G-33 </p>
                  </div>
               </div>
            </div>
         </div>
      </div>
      <!--#End Our footer Area -->
      <!-- The following is only needed when the video is in the html
         otherwise the who .hero__overlay html can be removed -->
      <div class="hero__overlay">
         <div class="hero__modal">
            <a class="hero__close" href="#">Close</a>
            <iframe allowscriptaccess="always" id="hero-video" class="hero__player" src="https://www.youtube.com/embed/1NSA8ycGfKg?enablejsapi=1&html5=1" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
         </div>
         <!-- /.hero__modal -->
      </div>
      <!-- /.hero__overlay -->
      <!-- jQuery JS -->
      <script src="assets/js/jquery-1.12.0.min.js"></script>
      <script src="assets/vendors/popup/lightbox.min.js"></script>
      <script type="text/javascript">
         $(document).ready(function() {
         $("div.bhoechie-tab-menu>div.list-group>a").click(function(e) {
           e.preventDefault();
           $(this).siblings('a.active').removeClass("active");
           $(this).addClass("active");
           var index = $(this).index();
           $("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active");
           $("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
         });
         });
      </script>
      <script type="text/javascript">
         $(document).ready(function(){
           $(".currency_year").hide();
             $("#radio1").click(function(){
                 $(".currency_year").hide();
                 $(".currency_month").show();
             });
             $("#radio2").click(function(){
                 $(".currency_month").hide();
                 $(".currency_year").show();
             });
         });

          $('.tabs_label').click(function(){
                      $('.tabs_label').removeClass('active_t');
                      $(this).addClass('active_t');

                  })

      </script>
      <!-- Bootstrap JS -->
      <script src="{{asset('assets2/js/bootstrap.min.js')}}"></script>
      <!-- Animate JS -->
      <script src="{{asset('assets2/vendors/animate/wow.min.js')}}"></script>
      <script src="{{asset('assets2/vendors/sidebar/main.js')}}"></script>
      <!-- Owlcarousel JS -->
      <script src="{{asset('assets2/vendors/owl_carousel/owl.carousel.min.js')}}"></script>
      <!-- Stellar JS-->
      <!-- Theme JS -->
      <script src="{{asset('assets2/js/theme.min.js')}}"></script>
   </body>
</html>









