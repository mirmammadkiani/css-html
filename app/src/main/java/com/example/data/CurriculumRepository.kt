package com.example.data

import com.example.model.CodeTarget
import com.example.model.CurriculumModule
import com.example.model.Lesson
import com.example.model.LessonStep
import com.example.model.LessonTopic
import com.example.model.PlaygroundTemplate
import com.example.model.ReferenceEntry
import com.example.model.ValidationCheck

object CurriculumRepository {

    val modules: List<CurriculumModule> = listOf(
        CurriculumModule(
            id = "mod_html_foundations",
            number = 1,
            title = "مبانی و ساختار معنایی HTML5",
            description = "درک عمیق سند استاندارد HTML، سلسله‌مراتب تیترها و تگ‌های معنایی (Semantic) بر اساس استاندارد WHATWG.",
            referenceBadge = "WHATWG HTML & MDN",
            topic = LessonTopic.HTML,
            lessons = listOf(
                Lesson(
                    id = "lesson_html_skeleton",
                    moduleId = "mod_html_foundations",
                    number = 1,
                    title = "آناتومی سند استاندارد HTML5",
                    subtitle = "DOCTYPE، ویژگی‌های lang و dir، و متادیتا",
                    topic = LessonTopic.HTML,
                    estimatedMinutes = 5,
                    steps = listOf(
                        LessonStep(
                            id = "step_1_1_doctype",
                            title = "ساختار پایه و زبان سند",
                            explanation = "هر سند استاندارد وب با اعلان <!DOCTYPE html> آغاز می‌شود تا مرورگر در حالت استاندارد (Standards Mode) رندر کند. برای سایت‌های فارسی، افزودن ویژگی‌های lang=\"fa\" و dir=\"rtl\" روی تگ <html> برای دسترس‌پذیری و موتورهای جستجو ضروری است.",
                            standardNote = "بر اساس مستندات WHATWG، نبود <!DOCTYPE html> باعث ورود مرورگر به حالت Quirks Mode و به‌هم‌ریختگی چیدمان CSS می‌شود.",
                            standardSource = "MDN Web Docs — Document Structure",
                            taskPrompt = "تگ <h1> را داخل <body> اضافه کنید و عنوان صفحه را بنویسید، همچنین <title> را داخل <head> کامل کنید.",
                            starterHtml = """<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
  <meta charset="UTF-8">
  <title></title>
</head>
<body>
  <!-- تیتر اصلی را اینجا بنویسید -->
</body>
</html>""",
                            starterCss = """body {
  font-family: sans-serif;
  background: #f8fafc;
  color: #0f172a;
  padding: 20px;
}""",
                            solutionHtml = """<!DOCTYPE html>
<html lang="fa" dir="rtl">
<head>
  <meta charset="UTF-8">
  <title>نخستین صفحه استاندارد</title>
</head>
<body>
  <h1>سلام دنیای وب!</h1>
</body>
</html>""",
                            solutionCss = """body {
  font-family: sans-serif;
  background: #f8fafc;
  color: #0f172a;
  padding: 20px;
}""",
                            defaultTab = CodeTarget.HTML,
                            checks = listOf(
                                ValidationCheck(
                                    description = "عنوان داخل تگ <title>",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<title>\\s*[^<\\s][^<]*</title>",
                                    errorHint = "داخل تگ <title>...</title> یک متن غیرخالی بنویسید."
                                ),
                                ValidationCheck(
                                    description = "تیتر اصلی <h1> داخل <body>",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<h1[^>]*>\\s*[^<\\s][^<]*</h1>",
                                    errorHint = "یک تگ <h1>متن دلخواه</h1> داخل <body> اضافه کنید."
                                )
                            ),
                            quickChips = listOf("<h1>", "</h1>", "<p>", "</p>", "<title>")
                        ),
                        LessonStep(
                            id = "step_1_2_hierarchy",
                            title = "سلسله‌مراتب تیترها و متن معنایی",
                            explanation = "تگ‌های <h1> تا <h6> ساختار درختی محتوا را می‌سازند و نباید صرفاً برای بزرگ کردن فونت استفاده شوند. برای تأکید معنایی از <strong> (اهمیت بالا) و <em> (تأکید لحن) استفاده کنید نه <b> یا <i>.",
                            standardNote = "در هر صفحه یا بخش مستقل بهتر است تنها یک <h1> وجود داشته باشد و سطوح تیترها بدون پرش (مثلاً h1 به h3) دنبال شوند.",
                            standardSource = "web.dev — Learn HTML: Headings",
                            taskPrompt = "یک زیرتیتر <h2> و یک پاراگراف <p> بسازید که درون آن از تگ <strong> استفاده شده باشد.",
                            starterHtml = """<article>
  <h1>راهنمای طراحی وب</h1>
  <!-- زیرتیتر h2 و پاراگراف p را اضافه کنید -->
</article>""",
                            starterCss = """article {
  background: #ffffff;
  padding: 18px;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
  line-height: 1.8;
}
h1 { color: #0284c7; margin-top: 0; }
strong { color: #e44d26; }""",
                            solutionHtml = """<article>
  <h1>راهنمای طراحی وب</h1>
  <h2>اهمیت ساختار معنایی</h2>
  <p>استفاده از کدهای <strong>استاندارد و تمیز</strong> باعث بهبود سئو می‌شود.</p>
</article>""",
                            solutionCss = """article {
  background: #ffffff;
  padding: 18px;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
  line-height: 1.8;
}
h1 { color: #0284c7; margin-top: 0; }
strong { color: #e44d26; }""",
                            defaultTab = CodeTarget.HTML,
                            checks = listOf(
                                ValidationCheck(
                                    description = "زیرتیتر <h2>",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<h2[^>]*>\\s*[^<\\s][^<]*</h2>",
                                    errorHint = "یک تگ <h2>...</h2> با متن مناسب اضافه کنید."
                                ),
                                ValidationCheck(
                                    description = "پاراگراف <p> دارای <strong>",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<p[^>]*>.*?<strong[^>]*>\\s*[^<\\s][^<]*</strong>.*?</p>",
                                    errorHint = "داخل تگ <p> از تگ <strong>...</strong> استفاده کنید."
                                )
                            ),
                            quickChips = listOf("<h2>", "</h2>", "<p>", "</p>", "<strong>", "</strong>")
                        )
                    )
                ),
                Lesson(
                    id = "lesson_semantic_tags",
                    moduleId = "mod_html_foundations",
                    number = 2,
                    title = "تگ‌های معنایی (Semantic HTML)",
                    subtitle = "جایگزینی divهای بی‌معنا با header، nav، main و footer",
                    topic = LessonTopic.HTML,
                    estimatedMinutes = 6,
                    steps = listOf(
                        LessonStep(
                            id = "step_2_1_landmarks",
                            title = "نشانگرهای صفحه (Landmarks)",
                            explanation = "به جای ساخت کل صفحه با <div>، از تگ‌های معنایی <header>، <nav>، <main> و <footer> استفاده کنید. صفحه‌خوان‌ها (Screen Readers) از این تگ‌ها برای پرش سریع کاربران نابینا میان بخش‌ها استفاده می‌کنند.",
                            standardNote = "در هر سند تنها یک عنصر <main> فعال باید وجود داشته باشد که محتوای اصلی و منحصربه‌فرد صفحه را دربرمی‌گیرد.",
                            standardSource = "MDN — Semantic HTML Elements",
                            taskPrompt = "ساختار زیر را تکمیل کنید: یک تگ <main> بسازید و درون آن یک <section> با تیتر <h2> قرار دهید، سپس یک <footer> در پایین اضافه کنید.",
                            starterHtml = """<header>
  <h1>وبلاگ فنی</h1>
  <nav>خانه | مقالات | تماس</nav>
</header>

<!-- اینجا تگ main و footer را بنویسید -->""",
                            starterCss = """body { margin: 0; font-family: sans-serif; background: #f1f5f9; }
header, main, footer { padding: 14px 18px; margin: 10px; border-radius: 10px; }
header { background: #0f172a; color: white; }
nav { color: #38bdf8; font-size: 13px; margin-top: 6px; }
main { background: white; border: 1px solid #cbd5e1; }
footer { background: #e2e8f0; color: #334155; font-size: 12px; text-align: center; }""",
                            solutionHtml = """<header>
  <h1>وبلاگ فنی</h1>
  <nav>خانه | مقالات | تماس</nav>
</header>

<main>
  <section>
    <h2>آخرین نوشته‌ها</h2>
    <p>محتوای اصلی در این بخش قرار می‌گیرد.</p>
  </section>
</main>

<footer>تمامی حقوق محفوظ است © ۱۴۰۵</footer>""",
                            solutionCss = """body { margin: 0; font-family: sans-serif; background: #f1f5f9; }
header, main, footer { padding: 14px 18px; margin: 10px; border-radius: 10px; }
header { background: #0f172a; color: white; }
nav { color: #38bdf8; font-size: 13px; margin-top: 6px; }
main { background: white; border: 1px solid #cbd5e1; }
footer { background: #e2e8f0; color: #334155; font-size: 12px; text-align: center; }""",
                            defaultTab = CodeTarget.HTML,
                            checks = listOf(
                                ValidationCheck(
                                    description = "تگ <main> و <section>",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<main[^>]*>.*?<section[^>]*>.*?</section>.*?</main>",
                                    errorHint = "تگ <section> را درون <main> قرار دهید."
                                ),
                                ValidationCheck(
                                    description = "تگ <footer>",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<footer[^>]*>\\s*[^<\\s].*?</footer>",
                                    errorHint = "یک تگ <footer>...</footer> در انتهای سند اضافه کنید."
                                )
                            ),
                            quickChips = listOf("<main>", "</main>", "<section>", "</section>", "<footer>", "</footer>")
                        )
                    )
                ),
                Lesson(
                    id = "lesson_links_lists",
                    moduleId = "mod_html_foundations",
                    number = 3,
                    title = "لیست‌ها، پیوندها و تصاویر دسترس‌پذیر",
                    subtitle = "استفاده اصولی از ul/li، تگ <a> و ویژگی alt",
                    topic = LessonTopic.HTML,
                    estimatedMinutes = 5,
                    steps = listOf(
                        LessonStep(
                            id = "step_3_1_nav_list",
                            title = "منوی استاندارد با <ul> و <a>",
                            explanation = "منوهای ناوبری در وب استاندارد با لیست نامرتب <ul> و آیتم‌های <li> درون <nav> ساخته می‌شوند. ویژگی href مقصد لینک را مشخص می‌کند و متن لینک باید گویا باشد (نه «اینجا کلیک کنید»).",
                            standardNote = "همیشه برای تصاویر تگ <img> از ویژگی alt استفاده کنید تا در صورت لود نشدن عکس یا استفاده از صفحه‌خوان، توضیح تصویر خوانده شود.",
                            standardSource = "W3C WAI — Navigation & Alt Text",
                            taskPrompt = "درون <nav> یک لیست <ul> با حداقل دو آیتم <li> بسازید که در هرکدام یک تگ <a href=\"#\"> باشد.",
                            starterHtml = """<nav class="menu">
  <!-- لیست منو را اینجا بسازید -->
</nav>""",
                            starterCss = """.menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  gap: 10px;
}
.menu a {
  display: inline-block;
  padding: 8px 14px;
  background: #0ea5e9;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-size: 14px;
}""",
                            solutionHtml = """<nav class="menu">
  <ul>
    <li><a href="#home">خانه</a></li>
    <li><a href="#docs">مستندات</a></li>
    <li><a href="#about">درباره ما</a></li>
  </ul>
</nav>""",
                            solutionCss = """.menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  gap: 10px;
}
.menu a {
  display: inline-block;
  padding: 8px 14px;
  background: #0ea5e9;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-size: 14px;
}""",
                            defaultTab = CodeTarget.HTML,
                            checks = listOf(
                                ValidationCheck(
                                    description = "ساختار <ul> در <nav>",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<ul[^>]*>.*?</ul>",
                                    errorHint = "تگ <ul>...</ul> را داخل <nav> بنویسید."
                                ),
                                ValidationCheck(
                                    description = "حداقل دو <li> دارای <a href=...>",
                                    target = CodeTarget.HTML,
                                    regexPattern = "(<li[^>]*>\\s*<a\\s+[^>]*href\\s*=\\s*[\"'][^\"']+[\"'][^>]*>.*?</a>\\s*</li>.*?){2,}",
                                    errorHint = "حداقل دو آیتم <li> بسازید که در هریک تگ <a href=\"#...\">متن</a> باشد."
                                )
                            ),
                            quickChips = listOf("<ul>", "</ul>", "<li>", "</li>", "<a href=\"#\">", "</a>")
                        )
                    )
                )
            )
        ),
        CurriculumModule(
            id = "mod_css_cascade",
            number = 2,
            title = "انتخابگرها، آبشار و رنگ‌ها در CSS",
            description = "درک الگوریتم Cascade و Specificity، انتخابگرهای کلاس و شناسه، و سیستم رنگ‌های مدرن.",
            referenceBadge = "W3C CSS Selectors",
            topic = LessonTopic.CSS,
            lessons = listOf(
                Lesson(
                    id = "lesson_css_selectors",
                    moduleId = "mod_css_cascade",
                    number = 4,
                    title = "انتخابگرهای عنصر، کلاس و شناسه",
                    subtitle = "چرا کلاس‌ها (.) قلب معماری CSS هستند؟",
                    topic = LessonTopic.CSS,
                    estimatedMinutes = 6,
                    steps = listOf(
                        LessonStep(
                            id = "step_4_1_class_selector",
                            title = "طراحی با انتخابگر کلاس (.class)",
                            explanation = "در CSS مدرن، به جای استایل‌دهی مستقیم به تگ‌ها یا استفاده زیاد از #id، از کلاس‌ها (مانند .badge یا .card) استفاده می‌کنیم تا استایل‌ها قابل استفاده مجدد باشند و اولویت (Specificity) کنترل‌شده باقی بماند.",
                            standardNote = "اولویت (Specificity) یک انتخابگر #id برابر (1,0,0)، یک .class برابر (0,1,0) و یک تگ ساده برابر (0,0,1) است.",
                            standardSource = "MDN — Specificity & Selectors",
                            taskPrompt = "در تب CSS، کلاس .highlight را با رنگ پس‌زمینه #fef3c7 و رنگ متن #92400e استایل دهید و شعاع گوشه border-radius: 10px بدهید.",
                            starterHtml = """<div class="card">
  <h2>نکته استاندارد</h2>
  <p class="highlight">استفاده از کلاس‌ها کد شما را ماژولار و خوانا می‌کند.</p>
</div>""",
                            starterCss = """.card {
  background: #ffffff;
  padding: 16px;
  border-radius: 14px;
  border: 1px solid #cbd5e1;
}

.highlight {
  padding: 12px;
  /* رنگ پس‌زمینه، رنگ متن و گوشه‌های گرد را اضافه کنید */
}""",
                            solutionHtml = """<div class="card">
  <h2>نکته استاندارد</h2>
  <p class="highlight">استفاده از کلاس‌ها کد شما را ماژولار و خوانا می‌کند.</p>
</div>""",
                            solutionCss = """.card {
  background: #ffffff;
  padding: 16px;
  border-radius: 14px;
  border: 1px solid #cbd5e1;
}

.highlight {
  padding: 12px;
  background-color: #fef3c7;
  color: #92400e;
  border-radius: 10px;
}""",
                            defaultTab = CodeTarget.CSS,
                            checks = listOf(
                                ValidationCheck(
                                    description = "تعریف background یا background-color در .highlight",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.highlight\\s*\\{[^}]*background(-color)?\\s*:",
                                    errorHint = "داخل .highlight ویژگی background یا background-color را مقداردهی کنید."
                                ),
                                ValidationCheck(
                                    description = "تعریف color و border-radius در .highlight",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.highlight\\s*\\{[^}]*(color\\s*:[^}]*border-radius\\s*:|border-radius\\s*:[^}]*color\\s*:)",
                                    errorHint = "هر دو ویژگی color و border-radius را داخل .highlight بنویسید."
                                )
                            ),
                            quickChips = listOf("background-color:", "color:", "border-radius:", ";", "#fef3c7", "#92400e", "10px")
                        )
                    )
                ),
                Lesson(
                    id = "lesson_css_typography",
                    moduleId = "mod_css_cascade",
                    number = 5,
                    title = "تایپوگرافی خوانا و کنتراست استاندارد",
                    subtitle = "line-height، font-weight و واحدهای خوانا",
                    topic = LessonTopic.CSS,
                    estimatedMinutes = 5,
                    steps = listOf(
                        LessonStep(
                            id = "step_5_1_line_height",
                            title = "فاصله خطوط و وزن فونت",
                            explanation = "برای متون فارسی، فاصله خطوط (line-height) بین 1.7 تا 1.9 خوانایی را به‌شدت افزایش می‌دهد. همچنین بهتر است line-height را بدون واحد بنویسید تا با اندازه فونت هر بخش متناسب شود.",
                            standardNote = "استاندارد دسترسی WCAG حداقل نسبت کنتراست 4.5:1 بین رنگ متن و پس‌زمینه را برای متن عادی توصیه می‌کند.",
                            standardSource = "web.dev — Typography & WCAG Contrast",
                            taskPrompt = "در CSS، برای .article-body ویژگی‌های line-height: 1.8 و font-size: 16px و color: #1e293b را تنظیم کنید.",
                            starterHtml = """<article class="article-box">
  <h2 class="article-title">چرا استانداردها مهم‌اند؟</h2>
  <p class="article-body">وقتی فاصله خطوط و کنتراست رنگی درست تنظیم شود، چشم کاربر هنگام مطالعه خسته نمی‌شود.</p>
</article>""",
                            starterCss = """.article-box {
  background: #ffffff;
  padding: 20px;
  border-radius: 16px;
}
.article-title {
  color: #0f172a;
  font-weight: 700;
  margin-top: 0;
}
.article-body {
  /* تنظیمات خوانایی متن را اینجا بنویسید */
}""",
                            solutionHtml = """<article class="article-box">
  <h2 class="article-title">چرا استانداردها مهم‌اند؟</h2>
  <p class="article-body">وقتی فاصله خطوط و کنتراست رنگی درست تنظیم شود، چشم کاربر هنگام مطالعه خسته نمی‌شود.</p>
</article>""",
                            solutionCss = """.article-box {
  background: #ffffff;
  padding: 20px;
  border-radius: 16px;
}
.article-title {
  color: #0f172a;
  font-weight: 700;
  margin-top: 0;
}
.article-body {
  line-height: 1.8;
  font-size: 16px;
  color: #1e293b;
}""",
                            defaultTab = CodeTarget.CSS,
                            checks = listOf(
                                ValidationCheck(
                                    description = "تنظیم line-height",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.article-body\\s*\\{[^}]*line-height\\s*:\\s*[0-9.]+",
                                    errorHint = "ویژگی line-height (مثلاً 1.8) را به .article-body اضافه کنید."
                                ),
                                ValidationCheck(
                                    description = "تنظیم font-size و color",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.article-body\\s*\\{[^}]*font-size\\s*:[^}]*color\\s*:|\\.article-body\\s*\\{[^}]*color\\s*:[^}]*font-size\\s*:",
                                    errorHint = "ویژگی‌های font-size و color را داخل .article-body مشخص کنید."
                                )
                            ),
                            quickChips = listOf("line-height:", "font-size:", "color:", "1.8;", "16px;", "#1e293b;")
                        )
                    )
                )
            )
        ),
        CurriculumModule(
            id = "mod_box_model",
            number = 3,
            title = "مدل جعبه‌ای (CSS Box Model)",
            description = "درک دقیق Content، Padding، Border، Margin و قانون طلایی box-sizing: border-box.",
            referenceBadge = "W3C Box Model L3",
            topic = LessonTopic.CSS,
            lessons = listOf(
                Lesson(
                    id = "lesson_box_model_anatomy",
                    moduleId = "mod_box_model",
                    number = 6,
                    title = "کالبدشکافی Padding، Border و Margin",
                    subtitle = "فاصله درونی در برابر فاصله بیرونی",
                    topic = LessonTopic.CSS,
                    estimatedMinutes = 6,
                    steps = listOf(
                        LessonStep(
                            id = "step_6_1_padding_margin",
                            title = "تنظیم فضاهای درونی و بیرونی کارت",
                            explanation = "هر عنصر در CSS یک جعبه مستطیلی است: padding فضای تنفس داخل کادر (میان متن و حاشیه) است، border خط دور جعبه است و margin فاصله جعبه تا عناصر اطراف است.",
                            standardNote = "حاشیه عمودی (Vertical Margin) بین دو عنصر هم‌تراز می‌تواند ادغام (Margin Collapse) شود؛ این رفتار عمدی استاندارد CSS است.",
                            standardSource = "MDN — The CSS Box Model",
                            taskPrompt = "در CSS برای کلاس .box مقدار padding: 20px، یک border: 2px solid #0ea5e9 و margin: 16px بنویسید.",
                            starterHtml = """<div class="wrapper">
  <div class="box">من یک جعبه استاندارد CSS هستم!</div>
  <div class="box">من جعبه دوم هستم.</div>
</div>""",
                            starterCss = """.wrapper {
  background: #e2e8f0;
  padding: 8px;
}
.box {
  background: #ffffff;
  border-radius: 10px;
  /* padding، border و margin را اینجا اضافه کنید */
}""",
                            solutionHtml = """<div class="wrapper">
  <div class="box">من یک جعبه استاندارد CSS هستم!</div>
  <div class="box">من جعبه دوم هستم.</div>
</div>""",
                            solutionCss = """.wrapper {
  background: #e2e8f0;
  padding: 8px;
}
.box {
  background: #ffffff;
  border-radius: 10px;
  padding: 20px;
  border: 2px solid #0ea5e9;
  margin: 16px;
}""",
                            defaultTab = CodeTarget.CSS,
                            checks = listOf(
                                ValidationCheck(
                                    description = "تنظیم padding",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.box\\s*\\{[^}]*padding\\s*:",
                                    errorHint = "ویژگی padding (مثلاً padding: 20px;) را اضافه کنید."
                                ),
                                ValidationCheck(
                                    description = "تنظیم border",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.box\\s*\\{[^}]*border\\s*:",
                                    errorHint = "ویژگی border (مثلاً border: 2px solid #0ea5e9;) را اضافه کنید."
                                ),
                                ValidationCheck(
                                    description = "تنظیم margin",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.box\\s*\\{[^}]*margin\\s*:",
                                    errorHint = "ویژگی margin (مثلاً margin: 16px;) را اضافه کنید."
                                )
                            ),
                            quickChips = listOf("padding:", "border:", "margin:", "20px;", "2px solid #0ea5e9;", "16px;")
                        ),
                        LessonStep(
                            id = "step_6_2_border_box",
                            title = "قانون طلایی box-sizing: border-box",
                            explanation = "به‌طور پیش‌فرض (content-box)، وقتی width: 100% می‌دهید و padding اضافه می‌کنید، عرض واقعی از 100% بیشتر شده و صفحه اسکرول افقی می‌خورد! با box-sizing: border-box، پدینگ و بوردر درون همان عرض محاسبه می‌شوند.",
                            standardNote = "تقریباً تمام پروژه‌های حرفه‌ای دنیا در ابتدای CSS خود از قاعده *, *::before, *::after { box-sizing: border-box; } استفاده می‌کنند.",
                            standardSource = "web.dev — Learn CSS: Box Model",
                            taskPrompt = "در CSS، به کلاس .panel ویژگی box-sizing: border-box; و width: 100%; را اضافه کنید تا از کادر بیرون نزند.",
                            starterHtml = """<div class="frame">
  <div class="panel">عرض ۱۰۰٪ بدون بیرون‌زدگی!</div>
</div>""",
                            starterCss = """.frame {
  width: 260px;
  border: 3px dashed #e44d26;
  padding: 6px;
}
.panel {
  width: 100%;
  padding: 24px;
  border: 4px solid #0284c7;
  background: #e0f2fe;
  /* box-sizing را اصلاح کنید */
}""",
                            solutionHtml = """<div class="frame">
  <div class="panel">عرض ۱۰۰٪ بدون بیرون‌زدگی!</div>
</div>""",
                            solutionCss = """.frame {
  width: 260px;
  border: 3px dashed #e44d26;
  padding: 6px;
}
.panel {
  box-sizing: border-box;
  width: 100%;
  padding: 24px;
  border: 4px solid #0284c7;
  background: #e0f2fe;
}""",
                            defaultTab = CodeTarget.CSS,
                            checks = listOf(
                                ValidationCheck(
                                    description = "استفاده از box-sizing: border-box",
                                    target = CodeTarget.CSS,
                                    regexPattern = "box-sizing\\s*:\\s*border-box",
                                    errorHint = "عبارت box-sizing: border-box; را به .panel اضافه کنید."
                                )
                            ),
                            quickChips = listOf("box-sizing:", "border-box;", "width:", "100%;")
                        )
                    )
                )
            )
        ),
        CurriculumModule(
            id = "mod_modern_layout",
            number = 4,
            title = "چیدمان مدرن: Flexbox و CSS Grid",
            description = "ساخت رابط‌های تراز و حرفه‌ای بدون float و hackهای قدیمی، با تکیه بر Flexbox و Grid.",
            referenceBadge = "W3C Flexbox & Grid",
            topic = LessonTopic.LAYOUT,
            lessons = listOf(
                Lesson(
                    id = "lesson_flexbox_basics",
                    moduleId = "mod_modern_layout",
                    number = 7,
                    title = "مبانی چیدمان تک‌بعدی Flexbox",
                    subtitle = "display: flex، تراز محور اصلی و متقاطع، و gap",
                    topic = LessonTopic.LAYOUT,
                    estimatedMinutes = 7,
                    steps = listOf(
                        LessonStep(
                            id = "step_7_1_flex_row",
                            title = "چینش افقی با فاصله منظم (gap)",
                            explanation = "با دادن display: flex به والد (Container)، فرزندان در یک ردیف قرار می‌گیرند. ویژگی justify-content چیدمان در محور اصلی و align-items تراز در محور عمودی را کنترل می‌کند. برای فاصله بین آیتم‌ها از gap استفاده کنید.",
                            standardNote = "ویژگی gap جایگزین مدرن و تمیزِ marginهای اضافی در Flexbox و Grid است.",
                            standardSource = "MDN — Basic Concepts of Flexbox",
                            taskPrompt = "کلاس .toolbar را با display: flex;، justify-content: space-between;، align-items: center; و gap: 12px; تکمیل کنید.",
                            starterHtml = """<header class="toolbar">
  <div class="logo">وب‌آموز</div>
  <button class="cta">شروع یادگیری</button>
</header>""",
                            starterCss = """.toolbar {
  background: #0f172a;
  color: white;
  padding: 12px 16px;
  border-radius: 12px;
  /* قوانین Flexbox را اینجا بنویسید */
}
.logo { font-weight: bold; font-size: 18px; }
.cta {
  background: #e44d26;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 8px;
}""",
                            solutionHtml = """<header class="toolbar">
  <div class="logo">وب‌آموز</div>
  <button class="cta">شروع یادگیری</button>
</header>""",
                            solutionCss = """.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  background: #0f172a;
  color: white;
  padding: 12px 16px;
  border-radius: 12px;
}
.logo { font-weight: bold; font-size: 18px; }
.cta {
  background: #e44d26;
  color: white;
  border: none;
  padding: 8px 14px;
  border-radius: 8px;
}""",
                            defaultTab = CodeTarget.CSS,
                            checks = listOf(
                                ValidationCheck(
                                    description = "فعال‌سازی display: flex",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.toolbar\\s*\\{[^}]*display\\s*:\\s*flex",
                                    errorHint = "عبارت display: flex; را در .toolbar بنویسید."
                                ),
                                ValidationCheck(
                                    description = "استفاده از justify-content و align-items",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.toolbar\\s*\\{[^}]*justify-content\\s*:[^}]*align-items\\s*:|\\.toolbar\\s*\\{[^}]*align-items\\s*:[^}]*justify-content\\s*:",
                                    errorHint = "ویژگی‌های justify-content و align-items را در .toolbar تنظیم کنید."
                                ),
                                ValidationCheck(
                                    description = "تنظیم فاصله با gap",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.toolbar\\s*\\{[^}]*gap\\s*:",
                                    errorHint = "ویژگی gap (مثلاً gap: 12px;) را اضافه کنید."
                                )
                            ),
                            quickChips = listOf("display: flex;", "justify-content: space-between;", "align-items: center;", "gap: 12px;")
                        )
                    )
                ),
                Lesson(
                    id = "lesson_css_grid",
                    moduleId = "mod_modern_layout",
                    number = 8,
                    title = "چیدمان دوبعدی با CSS Grid",
                    subtitle = "ستون‌های منعطف با واحد fr و تابع repeat()",
                    topic = LessonTopic.LAYOUT,
                    estimatedMinutes = 7,
                    steps = listOf(
                        LessonStep(
                            id = "step_8_1_grid_columns",
                            title = "ساخت شبکه کارت‌های هم‌اندازه",
                            explanation = "وقتی می‌خواهید همزمان سطر و ستون داشته باشید، CSS Grid بهترین ابزار است. واحد fr یعنی یک سهم از فضای باقیمانده. دستور grid-template-columns: repeat(2, 1fr) دو ستون دقیقاً هم‌عرض می‌سازد.",
                            standardNote = "تابع minmax(140px, 1fr) همراه با auto-fit شبکه‌ای می‌سازد که بدون حتی یک Media Query واکنش‌گراست!",
                            standardSource = "MDN — CSS Grid Layout",
                            taskPrompt = "در کلاس .grid-cards ویژگی display: grid;، grid-template-columns: repeat(2, 1fr); و gap: 12px; را بنویسید.",
                            starterHtml = """<div class="grid-cards">
  <div class="item">HTML معنایی</div>
  <div class="item">مدل جعبه‌ای</div>
  <div class="item">فلکس‌باکس</div>
  <div class="item">گرید مدرن</div>
</div>""",
                            starterCss = """.grid-cards {
  /* چیدمان Grid را اینجا بنویسید */
}
.item {
  background: #0284c7;
  color: white;
  padding: 18px 12px;
  border-radius: 12px;
  text-align: center;
  font-weight: bold;
}""",
                            solutionHtml = """<div class="grid-cards">
  <div class="item">HTML معنایی</div>
  <div class="item">مدل جعبه‌ای</div>
  <div class="item">فلکس‌باکس</div>
  <div class="item">گرید مدرن</div>
</div>""",
                            solutionCss = """.grid-cards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}
.item {
  background: #0284c7;
  color: white;
  padding: 18px 12px;
  border-radius: 12px;
  text-align: center;
  font-weight: bold;
}""",
                            defaultTab = CodeTarget.CSS,
                            checks = listOf(
                                ValidationCheck(
                                    description = "فعال‌سازی display: grid",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.grid-cards\\s*\\{[^}]*display\\s*:\\s*grid",
                                    errorHint = "عبارت display: grid; را اضافه کنید."
                                ),
                                ValidationCheck(
                                    description = "تعریف grid-template-columns با fr",
                                    target = CodeTarget.CSS,
                                    regexPattern = "grid-template-columns\\s*:[^;]*fr",
                                    errorHint = "ویژگی grid-template-columns را با واحد fr (مثلاً repeat(2, 1fr)) بنویسید."
                                ),
                                ValidationCheck(
                                    description = "تنظیم gap",
                                    target = CodeTarget.CSS,
                                    regexPattern = "\\.grid-cards\\s*\\{[^}]*gap\\s*:",
                                    errorHint = "ویژگی gap را بین کارت‌ها اضافه کنید."
                                )
                            ),
                            quickChips = listOf("display: grid;", "grid-template-columns:", "repeat(2, 1fr);", "gap: 12px;")
                        )
                    )
                )
            )
        ),
        CurriculumModule(
            id = "mod_responsive_forms",
            number = 5,
            title = "طراحی واکنش‌گرا و فرم‌های استاندارد",
            description = "کار با Media Queries، واحدهای نسبی و ساخت فرم‌های دسترس‌پذیر با label و input.",
            referenceBadge = "web.dev & WAI Forms",
            topic = LessonTopic.HTML,
            lessons = listOf(
                Lesson(
                    id = "lesson_responsive_media",
                    moduleId = "mod_responsive_forms",
                    number = 9,
                    title = "طراحی واکنش‌گرا با Media Queries",
                    subtitle = "رویکرد Mobile-First با @media (min-width)",
                    topic = LessonTopic.LAYOUT,
                    estimatedMinutes = 6,
                    steps = listOf(
                        LessonStep(
                            id = "step_9_1_media_query",
                            title = "تغییر چیدمان در نمایشگرهای عریض",
                            explanation = "در رویکرد استاندارد Mobile-First، ابتدا استایل موبایل (ستون تکی) را می‌نویسیم و سپس با @media (min-width: 480px) برای صفحات بزرگ‌تر چیدمان را دو ستونه می‌کنیم. دکمه تغییر نمای «موبایل / تمام‌صفحه» در پیش‌نمایش را امتحان کنید!",
                            standardNote = "فراموش نکنید در پروژه‌های واقعی تگ <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> در <head> حیاتی است.",
                            standardSource = "MDN — Using Media Queries",
                            taskPrompt = "در CSS یک بلاک @media (min-width: 480px) بنویسید که داخل آن .cards را به flex-direction: row; تغییر دهد.",
                            starterHtml = """<div class="cards">
  <div class="box">بخش اول</div>
  <div class="box">بخش دوم</div>
</div>""",
                            starterCss = """.cards {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.box {
  flex: 1;
  background: #10b981;
  color: white;
  padding: 18px;
  border-radius: 12px;
  text-align: center;
  font-weight: bold;
}

/* کوئری @media را برای عرض حداقل 480px اضافه کنید */""",
                            solutionHtml = """<div class="cards">
  <div class="box">بخش اول</div>
  <div class="box">بخش دوم</div>
</div>""",
                            solutionCss = """.cards {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.box {
  flex: 1;
  background: #10b981;
  color: white;
  padding: 18px;
  border-radius: 12px;
  text-align: center;
  font-weight: bold;
}

@media (min-width: 480px) {
  .cards {
    flex-direction: row;
  }
}""",
                            defaultTab = CodeTarget.CSS,
                            checks = listOf(
                                ValidationCheck(
                                    description = "بلاک @media (min-width: ...)",
                                    target = CodeTarget.CSS,
                                    regexPattern = "@media\\s*\\(\\s*min-width\\s*:\\s*\\d+px\\s*\\)",
                                    errorHint = "یک شرط @media (min-width: 480px) { ... } بنویسید."
                                ),
                                ValidationCheck(
                                    description = "تغییر به flex-direction: row",
                                    target = CodeTarget.CSS,
                                    regexPattern = "flex-direction\\s*:\\s*row",
                                    errorHint = "درون @media برای .cards مقدار flex-direction: row; تنظیم کنید."
                                )
                            ),
                            quickChips = listOf("@media (min-width: 480px) {", "flex-direction: row;", "}")
                        )
                    )
                ),
                Lesson(
                    id = "lesson_accessible_forms",
                    moduleId = "mod_responsive_forms",
                    number = 10,
                    title = "فرم‌های استاندارد و دسترس‌پذیر",
                    subtitle = "اتصال label به input و نوع‌های دقیق فیلدها",
                    topic = LessonTopic.HTML,
                    estimatedMinutes = 6,
                    steps = listOf(
                        LessonStep(
                            id = "step_10_1_label_for",
                            title = "اتصال معنایی label و input",
                            explanation = "هر فیلد ورودی <input> باید یک <label> متصل با ویژگی‌های یکسان for و id داشته باشد. علاوه بر کمک به صفحه‌خوان‌ها، باعث می‌شود لمس عنوان هم روی موبایل فیلد را فعال کند. همچنین از type=\"email\" برای کیبورد ایمیل و required برای اعتبارسنجی بومی مرورگر استفاده کنید.",
                            standardNote = "هرگز از placeholder به عنوان جایگزین <label> استفاده نکنید؛ با تایپ کاربر، placeholder ناپدید می‌شود.",
                            standardSource = "MDN — How to Structure a Web Form",
                            taskPrompt = "در HTML، یک <label for=\"email\"> و یک <input id=\"email\" type=\"email\" required> داخل <form> بسازید و دکمه <button type=\"submit\"> را اضافه کنید.",
                            starterHtml = """<form class="signup-form">
  <h2>عضویت در خبرنامه</h2>
  <!-- فیلد ایمیل و دکمه ارسال را بنویسید -->
</form>""",
                            starterCss = """.signup-form {
  background: white;
  padding: 18px;
  border-radius: 14px;
  border: 1px solid #cbd5e1;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
label { font-weight: bold; font-size: 14px; color: #334155; }
input {
  padding: 10px;
  border: 1px solid #94a3b8;
  border-radius: 8px;
  font-size: 14px;
}
button {
  background: #e44d26;
  color: white;
  border: none;
  padding: 11px;
  border-radius: 8px;
  font-weight: bold;
}""",
                            solutionHtml = """<form class="signup-form">
  <h2>عضویت در خبرنامه</h2>
  <label for="email">نشانی ایمیل</label>
  <input id="email" type="email" placeholder="you@example.com" required>
  <button type="submit">ثبت‌نام</button>
</form>""",
                            solutionCss = """.signup-form {
  background: white;
  padding: 18px;
  border-radius: 14px;
  border: 1px solid #cbd5e1;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
label { font-weight: bold; font-size: 14px; color: #334155; }
input {
  padding: 10px;
  border: 1px solid #94a3b8;
  border-radius: 8px;
  font-size: 14px;
}
button {
  background: #e44d26;
  color: white;
  border: none;
  padding: 11px;
  border-radius: 8px;
  font-weight: bold;
}""",
                            defaultTab = CodeTarget.HTML,
                            checks = listOf(
                                ValidationCheck(
                                    description = "تگ <label for=\"email\">",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<label\\s+[^>]*for\\s*=\\s*[\"']email[\"'][^>]*>.*?</label>",
                                    errorHint = "یک تگ <label for=\"email\">...</label> اضافه کنید."
                                ),
                                ValidationCheck(
                                    description = "فیلد <input id=\"email\" type=\"email\">",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<input\\s+[^>]*id\\s*=\\s*[\"']email[\"'][^>]*>",
                                    errorHint = "تگ <input id=\"email\" type=\"email\" required> را اضافه کنید."
                                ),
                                ValidationCheck(
                                    description = "دکمه <button type=\"submit\">",
                                    target = CodeTarget.HTML,
                                    regexPattern = "<button[^>]*>.*?</button>",
                                    errorHint = "یک دکمه <button type=\"submit\">ثبت‌نام</button> اضافه کنید."
                                )
                            ),
                            quickChips = listOf("<label for=\"email\">", "</label>", "<input id=\"email\" type=\"email\" required>", "<button type=\"submit\">", "</button>")
                        )
                    )
                )
            )
        )
    )

    val allLessons: List<Lesson> = modules.flatMap { it.lessons }
    val allStepsCount: Int = allLessons.sumOf { it.steps.size }

    fun findLesson(lessonId: String): Lesson? = allLessons.find { it.id == lessonId }

    val playgroundTemplates: List<PlaygroundTemplate> = listOf(
        PlaygroundTemplate(
            id = "tpl_profile_card",
            title = "کارت پروفایل مدرن",
            subtitle = "ترکیب Flexbox، Box Model و تایپوگرافی",
            html = """<article class="profile-card">
  <div class="avatar">A</div>
  <h1>امیر کیانی</h1>
  <p class="role">توسعه‌دهنده فرانت‌اند و طراح وب</p>
  <div class="tags">
    <span>HTML5</span>
    <span>CSS Grid</span>
    <span>A11y</span>
  </div>
  <a href="#portfolio" class="btn">مشاهده نمونه‌کارها</a>
</article>""",
            css = """body {
  background: #0f172a;
  display: flex;
  justify-content: center;
  padding: 20px;
  font-family: sans-serif;
}
.profile-card {
  background: #1e293b;
  color: #f8fafc;
  width: 100%;
  max-width: 320px;
  padding: 24px;
  border-radius: 20px;
  text-align: center;
  border: 1px solid #334155;
}
.avatar {
  width: 64px;
  height: 64px;
  margin: 0 auto 12px;
  border-radius: 50%;
  background: linear-gradient(135deg, #e44d26, #0ea5e9);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 26px;
  font-weight: bold;
}
h1 { margin: 8px 0 4px; font-size: 20px; }
.role { color: #94a3b8; font-size: 13px; margin: 0 0 16px; }
.tags {
  display: flex;
  justify-content: center;
  gap: 8px;
  margin-bottom: 18px;
}
.tags span {
  background: #0f172a;
  color: #38bdf8;
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 12px;
}
.btn {
  display: block;
  background: #0ea5e9;
  color: white;
  text-decoration: none;
  padding: 10px;
  border-radius: 10px;
  font-weight: bold;
}"""
        ),
        PlaygroundTemplate(
            id = "tpl_pricing_grid",
            title = "جدول پلن‌ها با CSS Grid",
            subtitle = "چیدمان واکنش‌گرا بدون Media Query",
            html = """<section class="plans">
  <div class="plan">
    <h2>پایه</h2>
    <p class="price">رایگان</p>
    <p>مناسب برای شروع یادگیری</p>
  </div>
  <div class="plan featured">
    <h2>حرفه‌ای</h2>
    <p class="price">استاندارد جهانی</p>
    <p>پروژه‌های عملی و تمرین نامحدود</p>
  </div>
</section>""",
            css = """body { font-family: sans-serif; background: #f8fafc; padding: 16px; }
.plans {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  gap: 12px;
}
.plan {
  background: white;
  padding: 18px;
  border-radius: 14px;
  border: 1px solid #cbd5e1;
  text-align: center;
}
.featured {
  border: 2px solid #0284c7;
  background: #f0f9ff;
}
.price { font-size: 18px; font-weight: bold; color: #e44d26; }"""
        ),
        PlaygroundTemplate(
            id = "tpl_semantic_article",
            title = "ساختار مقاله معنایی",
            subtitle = "استفاده درست از article، figure و time",
            html = """<article class="post">
  <header>
    <span class="badge">استاندارد وب</span>
    <h1>چرا Semantic HTML اهمیت دارد؟</h1>
    <time datetime="2026-11-13">۲۲ آبان ۱۴۰۵</time>
  </header>
  <p>کدهای معنایی هم برای <strong>موتورهای جستجو</strong> و هم برای <em>دسترس‌پذیری کاربران</em> ضروری هستند.</p>
</article>""",
            css = """.post {
  background: #ffffff;
  padding: 20px;
  border-radius: 16px;
  border-right: 4px solid #e44d26;
  line-height: 1.8;
  font-family: sans-serif;
}
.badge {
  background: #ffedd5;
  color: #c2410c;
  padding: 3px 10px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: bold;
}
time { color: #64748b; font-size: 12px; }"""
        )
    )

    val referenceEntries: List<ReferenceEntry> = listOf(
        ReferenceEntry(
            id = "ref_doctype",
            category = "ساختار HTML",
            syntax = "<!DOCTYPE html>",
            title = "اعلان نوع سند HTML5",
            summary = "باید نخستین خط فایل HTML باشد تا مرورگر به جای Quirks Mode از رندر استاندارد پیروی کند.",
            standardDoc = "WHATWG HTML Living Standard §13.1.1",
            sampleHtml = "<!DOCTYPE html>\n<html lang=\"fa\" dir=\"rtl\">\n<body>\n  <h1>سند استاندارد</h1>\n</body>\n</html>",
            sampleCss = "body { font-family: sans-serif; padding: 16px; }"
        ),
        ReferenceEntry(
            id = "ref_landmarks",
            category = "HTML معنایی",
            syntax = "<header> <nav> <main> <footer>",
            title = "تگ‌های نشانگر (Landmarks)",
            summary = "به جای divهای تو در تو، نقش هر بخش را برای موتورهای جستجو و ابزارهای صفحه‌خوان تعریف می‌کنند.",
            standardDoc = "MDN Web Docs — Landmarks",
            sampleHtml = "<header>سربرگ سایت</header>\n<main>\n  <article>محتوای اصلی</article>\n</main>\n<footer>پاورقی</footer>",
            sampleCss = "header, main, footer { padding: 12px; margin: 6px 0; border-radius: 8px; background: #e2e8f0; }"
        ),
        ReferenceEntry(
            id = "ref_img_alt",
            category = "HTML معنایی",
            syntax = "<img src=\"...\" alt=\"توضیح\">",
            title = "تصویر با متن جایگزین دسترس‌پذیر",
            summary = "ویژگی alt برای دسترسی‌پذیری (WCAG) و SEO الزامی است. برای تصاویر تزیینی از alt=\"\" استفاده کنید.",
            standardDoc = "W3C WAI — Images Tutorial",
            sampleHtml = "<figure>\n  <div style=\"background:#0ea5e9;color:white;padding:24px;border-radius:10px;text-align:center\">تصویر نمونه</div>\n  <figcaption>توضیح زیرنویس تصویر با تگ figcaption</figcaption>\n</figure>",
            sampleCss = "figcaption { font-size: 13px; color: #475569; margin-top: 6px; text-align: center; }"
        ),
        ReferenceEntry(
            id = "ref_specificity",
            category = "انتخابگر CSS",
            syntax = "tag (0,0,1) < .class (0,1,0) < #id (1,0,0)",
            title = "سلسله‌مراتب اولویت (Specificity)",
            summary = "مرورگر بر اساس وزن انتخابگر تصمیم می‌گیرد کدام قانون CSS اعمال شود؛ از !important و #id پرهیز کنید.",
            standardDoc = "W3C CSS Selectors Level 4",
            sampleHtml = "<p id=\"special\" class=\"note\">وزن انتخابگرها را تست کنید</p>",
            sampleCss = "p { color: gray; }\n.note { color: #0284c7; font-weight: bold; }\n/* #id از کلاس قوی‌تر است */"
        ),
        ReferenceEntry(
            id = "ref_box_sizing",
            category = "Box Model",
            syntax = "box-sizing: border-box;",
            title = "محاسبه هوشمند عرض و ارتفاع",
            summary = "باعث می‌شود padding و border به width و height اضافه نشوند و از اسکرول افقی جلوگیری شود.",
            standardDoc = "W3C CSS Box Sizing Level 3",
            sampleHtml = "<div class=\"box\">عرض دقیق ۲۰۰px شامل پدینگ و حاشیه</div>",
            sampleCss = ".box {\n  box-sizing: border-box;\n  width: 200px;\n  padding: 20px;\n  border: 4px solid #e44d26;\n  background: #ffedd5;\n}"
        ),
        ReferenceEntry(
            id = "ref_flexbox",
            category = "Flexbox",
            syntax = "display: flex; justify-content; align-items; gap;",
            title = "چیدمان تک‌بعدی منعطف",
            summary = "بهترین ابزار برای نوارهای ناوبری، کارت‌های افقی و تراز وسط عمودی/افقی عناصر.",
            standardDoc = "W3C CSS Flexible Box Layout",
            sampleHtml = "<div class=\"row\">\n  <div>۱</div><div>۲</div><div>۳</div>\n</div>",
            sampleCss = ".row {\n  display: flex;\n  justify-content: space-around;\n  align-items: center;\n  gap: 10px;\n  background: #0f172a;\n  padding: 16px;\n  border-radius: 12px;\n}\n.row div {\n  background: #38bdf8;\n  padding: 12px 20px;\n  border-radius: 8px;\n  font-weight: bold;\n}"
        ),
        ReferenceEntry(
            id = "ref_grid",
            category = "CSS Grid",
            syntax = "display: grid; grid-template-columns: repeat(2, 1fr);",
            title = "چیدمان دوبعدی سطر و ستونی",
            summary = "ساخت شبکه‌های پیچیده و واکنش‌گرا با واحد fr و قابلیت gap.",
            standardDoc = "W3C CSS Grid Layout Level 1",
            sampleHtml = "<div class=\"grid\">\n  <div>A</div><div>B</div><div>C</div><div>D</div>\n</div>",
            sampleCss = ".grid {\n  display: grid;\n  grid-template-columns: repeat(2, 1fr);\n  gap: 10px;\n}\n.grid div {\n  background: #10b981;\n  color: white;\n  padding: 20px;\n  border-radius: 10px;\n  text-align: center;\n}"
        ),
        ReferenceEntry(
            id = "ref_media_query",
            category = "واکنش‌گرا",
            syntax = "@media (min-width: 480px) { ... }",
            title = "استایل‌دهی شرطی بر اساس عرض صفحه",
            summary = "پایه طراحی Mobile-First: استایل پایه برای موبایل و ارتقای چیدمان برای تبلت و دسکتاپ.",
            standardDoc = "MDN — Responsive Web Design",
            sampleHtml = "<div class=\"responsive-box\">عرض پنجره را با دکمه بالا تغییر دهید!</div>",
            sampleCss = ".responsive-box {\n  background: #e44d26;\n  color: white;\n  padding: 20px;\n  border-radius: 12px;\n}\n@media (min-width: 480px) {\n  .responsive-box { background: #0284c7; }\n}"
        )
    )
}
