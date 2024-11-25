INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Shape of You'), 'The club isn’t the best place to find a lover'),
(2, (SELECT song_id FROM songs WHERE title = 'Shape of You'), 'So the bar is where I go'),
(3, (SELECT song_id FROM songs WHERE title = 'Shape of You'), 'Me and my friends at the table doing shots'),
(4, (SELECT song_id FROM songs WHERE title = 'Shape of You'), 'Drinking fast and then we talk slow'),
(5, (SELECT song_id FROM songs WHERE title = 'Shape of You'), 'Come over and start up a conversation with just me');


INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Blinding Lights'), 'I said, ooh, I’m blinded by the lights'),
(2, (SELECT song_id FROM songs WHERE title = 'Blinding Lights'), 'No, I can’t sleep until I feel your touch'),
(3, (SELECT song_id FROM songs WHERE title = 'Blinding Lights'), 'I said, ooh, I’m drowning in the night'),
(4, (SELECT song_id FROM songs WHERE title = 'Blinding Lights'), 'Oh, when I’m like this, you’re the one I trust'),
(5, (SELECT song_id FROM songs WHERE title = 'Blinding Lights'), 'I’m running out of time');


INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Uptown Funk'), 'This hit, that ice cold'),
(2, (SELECT song_id FROM songs WHERE title = 'Uptown Funk'), 'Michelle Pfeiffer, that white gold'),
(3, (SELECT song_id FROM songs WHERE title = 'Uptown Funk'), 'This one for them hood girls'),
(4, (SELECT song_id FROM songs WHERE title = 'Uptown Funk'), 'Them good girls, straight masterpieces'),
(5, (SELECT song_id FROM songs WHERE title = 'Uptown Funk'), 'Stylin’, whilin’, livin’ it up in the city');


INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Shake It Off'), 'Cause the players gonna play, play, play, play, play'),
(2, (SELECT song_id FROM songs WHERE title = 'Shake It Off'), 'And the haters gonna hate, hate, hate, hate, hate'),
(3, (SELECT song_id FROM songs WHERE title = 'Shake It Off'), 'Baby, I’m just gonna shake, shake, shake, shake, shake'),
(4, (SELECT song_id FROM songs WHERE title = 'Shake It Off'), 'I shake it off, I shake it off'),
(5, (SELECT song_id FROM songs WHERE title = 'Shake It Off'), 'Heartbreakers gonna break, break, break, break, break');


INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 'Hey, I just met you'),
(2, (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 'And this is crazy'),
(3, (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 'But here’s my number'),
(4, (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 'So call me, maybe'),
(5, (SELECT song_id FROM songs WHERE title = 'Call Me Maybe'), 'It’s hard to look right at you, baby');


INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Counting Stars'), 'Lately, I’ve been, I’ve been losing sleep'),
(2, (SELECT song_id FROM songs WHERE title = 'Counting Stars'), 'Dreaming about the things that we could be'),
(3, (SELECT song_id FROM songs WHERE title = 'Counting Stars'), 'But baby, I’ve been, I’ve been praying hard'),
(4, (SELECT song_id FROM songs WHERE title = 'Counting Stars'), 'Said no more counting dollars'),
(5, (SELECT song_id FROM songs WHERE title = 'Counting Stars'), 'We’ll be counting stars');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Rolling in the Deep'), 'We could have had it all'),
(2, (SELECT song_id FROM songs WHERE title = 'Rolling in the Deep'), 'Rolling in the deep'),
(3, (SELECT song_id FROM songs WHERE title = 'Rolling in the Deep'), 'You had my heart inside of your hand'),
(4, (SELECT song_id FROM songs WHERE title = 'Rolling in the Deep'), 'And you played it to the beat'),
(5, (SELECT song_id FROM songs WHERE title = 'Rolling in the Deep'), 'Baby, I have no story to be told');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Someone Like You'), 'Never mind, I’ll find someone like you'),
(2, (SELECT song_id FROM songs WHERE title = 'Someone Like You'), 'I wish nothing but the best for you, too'),
(3, (SELECT song_id FROM songs WHERE title = 'Someone Like You'), 'Don’t forget me, I beg'),
(4, (SELECT song_id FROM songs WHERE title = 'Someone Like You'), 'I remember you said'),
(5, (SELECT song_id FROM songs WHERE title = 'Someone Like You'), 'Sometimes it lasts in love, but sometimes it hurts instead');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Photograph'), 'We keep this love in a photograph'),
(2, (SELECT song_id FROM songs WHERE title = 'Photograph'), 'We made these memories for ourselves'),
(3, (SELECT song_id FROM songs WHERE title = 'Photograph'), 'Where our eyes are never closing'),
(4, (SELECT song_id FROM songs WHERE title = 'Photograph'), 'Hearts are never broken'),
(5, (SELECT song_id FROM songs WHERE title = 'Photograph'), 'And time’s forever frozen still');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Firework'), 'Cause baby, you’re a firework'),
(2, (SELECT song_id FROM songs WHERE title = 'Firework'), 'Come on, show ’em what you’re worth'),
(3, (SELECT song_id FROM songs WHERE title = 'Firework'), 'Make ’em go, "Oh, oh, oh!"'),
(4, (SELECT song_id FROM songs WHERE title = 'Firework'), 'As you shoot across the sky-y-y'),
(5, (SELECT song_id FROM songs WHERE title = 'Firework'), 'Boom, boom, boom');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Despacito' AND difficulty_level = 'easy'), 'Sí, sabes que ya llevo un rato mirándote'),
(2, (SELECT song_id FROM songs WHERE title = 'Despacito' AND difficulty_level = 'easy'), 'Tengo que bailar contigo hoy'),
(3, (SELECT song_id FROM songs WHERE title = 'Despacito' AND difficulty_level = 'easy'), 'Vi que tu mirada ya estaba llamándome'),
(4, (SELECT song_id FROM songs WHERE title = 'Despacito' AND difficulty_level = 'easy'), 'Muéstrame el camino que yo voy'),
(5, (SELECT song_id FROM songs WHERE title = 'Despacito' AND difficulty_level = 'easy'), 'Despacito, quiero respirar tu cuello despacito');


INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Stay'), 'All along it was a fever'),
(2, (SELECT song_id FROM songs WHERE title = 'Stay'), 'A cold sweat, hot-headed believer'),
(3, (SELECT song_id FROM songs WHERE title = 'Stay'), 'I threw my hands in the air, said, "Show me something"'),
(4, (SELECT song_id FROM songs WHERE title = 'Stay'), 'He said, "If you dare, come a little closer"'),
(5, (SELECT song_id FROM songs WHERE title = 'Stay'), 'Round and around and around and around we go');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Senorita'), 'I love it when you call me señorita'),
(2, (SELECT song_id FROM songs WHERE title = 'Senorita'), 'I wish I could pretend I didn’t need ya'),
(3, (SELECT song_id FROM songs WHERE title = 'Senorita'), 'But every touch is ooh-la-la-la'),
(4, (SELECT song_id FROM songs WHERE title = 'Senorita'), 'It’s true, la-la-la'),
(5, (SELECT song_id FROM songs WHERE title = 'Senorita'), 'Ooh, I should be running, ooh, you keep me coming for ya');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Cheap Thrills'), 'Baby, I don’t need dollar bills to have fun tonight'),
(2, (SELECT song_id FROM songs WHERE title = 'Cheap Thrills'), '(I love cheap thrills!)'),
(3, (SELECT song_id FROM songs WHERE title = 'Cheap Thrills'), 'Baby, I don’t need dollar bills to have fun tonight'),
(4, (SELECT song_id FROM songs WHERE title = 'Cheap Thrills'), '(I love cheap thrills!)'),
(5, (SELECT song_id FROM songs WHERE title = 'Cheap Thrills'), 'But I don’t need no money');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Bad Guy'), 'So you’re a tough guy, like it really rough guy'),
(2, (SELECT song_id FROM songs WHERE title = 'Bad Guy'), 'Just can’t get enough guy, chest always so puffed guy'),
(3, (SELECT song_id FROM songs WHERE title = 'Bad Guy'), 'I’m that bad type, make your mama sad type'),
(4, (SELECT song_id FROM songs WHERE title = 'Bad Guy'), 'Make your girlfriend mad type, might seduce your dad type'),
(5, (SELECT song_id FROM songs WHERE title = 'Bad Guy'), 'I’m the bad guy, duh');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Perfect'), 'I found a love for me'),
(2, (SELECT song_id FROM songs WHERE title = 'Perfect'), 'Darling, just dive right in and follow my lead'),
(3, (SELECT song_id FROM songs WHERE title = 'Perfect'), 'Well, I found a girl, beautiful and sweet'),
(4, (SELECT song_id FROM songs WHERE title = 'Perfect'), 'Oh, I never knew you were the someone waiting for me'),
(5, (SELECT song_id FROM songs WHERE title = 'Perfect'), '‘Cause we were just kids when we fell in love');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Hello'), 'Hello, it’s me'),
(2, (SELECT song_id FROM songs WHERE title = 'Hello'), 'I was wondering if after all these years you’d like to meet'),
(3, (SELECT song_id FROM songs WHERE title = 'Hello'), 'To go over everything'),
(4, (SELECT song_id FROM songs WHERE title = 'Hello'), 'They say that time’s supposed to heal ya, but I ain’t done much healing'),
(5, (SELECT song_id FROM songs WHERE title = 'Hello'), 'Hello, can you hear me?');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'All of Me'), 'Cause all of me loves all of you'),
(2, (SELECT song_id FROM songs WHERE title = 'All of Me'), 'Love your curves and all your edges'),
(3, (SELECT song_id FROM songs WHERE title = 'All of Me'), 'All your perfect imperfections'),
(4, (SELECT song_id FROM songs WHERE title = 'All of Me'), 'Give your all to me, I’ll give my all to you'),
(5, (SELECT song_id FROM songs WHERE title = 'All of Me'), 'You’re my end and my beginning');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Love Yourself'), 'Cause if you like the way you look that much'),
(2, (SELECT song_id FROM songs WHERE title = 'Love Yourself'), 'Oh, baby, you should go and love yourself'),
(3, (SELECT song_id FROM songs WHERE title = 'Love Yourself'), 'And if you think that I’m still holdin’ on to somethin’'),
(4, (SELECT song_id FROM songs WHERE title = 'Love Yourself'), 'You should go and love yourself'),
(5, (SELECT song_id FROM songs WHERE title = 'Love Yourself'), 'For all the times that you made me feel small');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Happy'), 'Because I’m happy'),
(2, (SELECT song_id FROM songs WHERE title = 'Happy'), 'Clap along if you feel like a room without a roof'),
(3, (SELECT song_id FROM songs WHERE title = 'Happy'), 'Because I’m happy'),
(4, (SELECT song_id FROM songs WHERE title = 'Happy'), 'Clap along if you feel like happiness is the truth'),
(5, (SELECT song_id FROM songs WHERE title = 'Happy'), 'Because I’m happy');


INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Just the Way You Are'), 'Oh, her eyes, her eyes'),
(2, (SELECT song_id FROM songs WHERE title = 'Just the Way You Are'), 'Make the stars look like they’re not shining'),
(3, (SELECT song_id FROM songs WHERE title = 'Just the Way You Are'), 'Her hair, her hair'),
(4, (SELECT song_id FROM songs WHERE title = 'Just the Way You Are'), 'Falls perfectly without her trying'),
(5, (SELECT song_id FROM songs WHERE title = 'Just the Way You Are'), 'She’s so beautiful, and I tell her every day'),
(1, (SELECT song_id FROM songs WHERE title = 'Grenade'), 'Easy come, easy go'),
(2, (SELECT song_id FROM songs WHERE title = 'Grenade'), 'That’s just how you live, oh'),
(3, (SELECT song_id FROM songs WHERE title = 'Grenade'), 'Take, take, take it all, but you never give'),
(4, (SELECT song_id FROM songs WHERE title = 'Grenade'), 'Should’ve known you was trouble from the first kiss'),
(5, (SELECT song_id FROM songs WHERE title = 'Grenade'), 'Had your eyes wide open, why were they open?'),
(1, (SELECT song_id FROM songs WHERE title = 'Roar'), 'I got the eye of the tiger, a fighter'),
(2, (SELECT song_id FROM songs WHERE title = 'Roar'), 'Dancing through the fire'),
(3, (SELECT song_id FROM songs WHERE title = 'Roar'), '‘Cause I am a champion'),
(4, (SELECT song_id FROM songs WHERE title = 'Roar'), 'And you’re gonna hear me roar'),
(5, (SELECT song_id FROM songs WHERE title = 'Roar'), 'Louder, louder than a lion'),
(1, (SELECT song_id FROM songs WHERE title = 'Let Her Go'), 'Well you only need the light when it’s burning low'),
(2, (SELECT song_id FROM songs WHERE title = 'Let Her Go'), 'Only miss the sun when it starts to snow'),
(3, (SELECT song_id FROM songs WHERE title = 'Let Her Go'), 'Only know you love her when you let her go'),
(4, (SELECT song_id FROM songs WHERE title = 'Let Her Go'), 'Only know you’ve been high when you’re feeling low'),
(5, (SELECT song_id FROM songs WHERE title = 'Let Her Go'), 'Only hate the road when you’re missing home'),
(1, (SELECT song_id FROM songs WHERE title = 'You Belong With Me'), 'If you could see that I’m the one who understands you'),
(2, (SELECT song_id FROM songs WHERE title = 'You Belong With Me'), 'Been here all along, so why can’t you see?'),
(3, (SELECT song_id FROM songs WHERE title = 'You Belong With Me'), 'You belong with me'),
(4, (SELECT song_id FROM songs WHERE title = 'You Belong With Me'), 'You belong with me'),
(5, (SELECT song_id FROM songs WHERE title = 'You Belong With Me'), 'Walk in the streets with you in your worn-out jeans');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Chasing Cars'), 'If I lay here'),
(2, (SELECT song_id FROM songs WHERE title = 'Chasing Cars'), 'If I just lay here'),
(3, (SELECT song_id FROM songs WHERE title = 'Chasing Cars'), 'Would you lie with me and just forget the world?'),
(4, (SELECT song_id FROM songs WHERE title = 'Chasing Cars'), 'Forget what we’re told'),
(5, (SELECT song_id FROM songs WHERE title = 'Chasing Cars'), 'Before we get too old'),
(1, (SELECT song_id FROM songs WHERE title = 'Boulevard of Broken Dreams'), 'I walk a lonely road'),
(2, (SELECT song_id FROM songs WHERE title = 'Boulevard of Broken Dreams'), 'The only one that I have ever known'),
(3, (SELECT song_id FROM songs WHERE title = 'Boulevard of Broken Dreams'), 'Don’t know where it goes'),
(4, (SELECT song_id FROM songs WHERE title = 'Boulevard of Broken Dreams'), 'But it’s home to me, and I walk alone'),
(5, (SELECT song_id FROM songs WHERE title = 'Boulevard of Broken Dreams'), 'I walk this empty street'),
(1, (SELECT song_id FROM songs WHERE title = 'The Middle'), 'Hey, don’t write yourself off yet'),
(2, (SELECT song_id FROM songs WHERE title = 'The Middle'), 'It’s only in your head you feel left out or looked down on'),
(3, (SELECT song_id FROM songs WHERE title = 'The Middle'), 'Just try your best, try everything you can'),
(4, (SELECT song_id FROM songs WHERE title = 'The Middle'), 'And don’t you worry what they tell themselves when you’re away'),
(5, (SELECT song_id FROM songs WHERE title = 'The Middle'), 'It just takes some time'),
(1, (SELECT song_id FROM songs WHERE title = 'Say You Won''t Let Go'), 'I met you in the dark, you lit me up'),
(2, (SELECT song_id FROM songs WHERE title = 'Say You Won''t Let Go'), 'You made me feel as though I was enough'),
(3, (SELECT song_id FROM songs WHERE title = 'Say You Won''t Let Go'), 'We danced the night away, we drank too much'),
(4, (SELECT song_id FROM songs WHERE title = 'Say You Won''t Let Go'), 'I held your hair back when you were throwing up'),
(5, (SELECT song_id FROM songs WHERE title = 'Say You Won''t Let Go'), 'Then you smiled over your shoulder'),
(1, (SELECT song_id FROM songs WHERE title = 'Before You Go'), 'Before you go'),
(2, (SELECT song_id FROM songs WHERE title = 'Before You Go'), 'Was there something I could’ve said to make your heart beat better?'),
(3, (SELECT song_id FROM songs WHERE title = 'Before You Go'), 'If only I’d have known you had a storm to weather'),
(4, (SELECT song_id FROM songs WHERE title = 'Before You Go'), 'So, before you go'),
(5, (SELECT song_id FROM songs WHERE title = 'Before You Go'), 'Was there something I could’ve said to make it all stop hurting?');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Take Me to Church'), 'Take me to church'),
(2, (SELECT song_id FROM songs WHERE title = 'Take Me to Church'), 'I’ll worship like a dog at the shrine of your lies'),
(3, (SELECT song_id FROM songs WHERE title = 'Take Me to Church'), 'I’ll tell you my sins and you can sharpen your knife'),
(4, (SELECT song_id FROM songs WHERE title = 'Take Me to Church'), 'Offer me that deathless death'),
(5, (SELECT song_id FROM songs WHERE title = 'Take Me to Church'), 'Good God, let me give you my life'),
(1, (SELECT song_id FROM songs WHERE title = 'Apologize'), 'I’m holding on your rope'),
(2, (SELECT song_id FROM songs WHERE title = 'Apologize'), 'Got me ten feet off the ground'),
(3, (SELECT song_id FROM songs WHERE title = 'Apologize'), 'And I’m hearing what you say, but I just can’t make a sound'),
(4, (SELECT song_id FROM songs WHERE title = 'Apologize'), 'You tell me that you need me, then you go and cut me down'),
(5, (SELECT song_id FROM songs WHERE title = 'Apologize'), 'But wait'),
(1, (SELECT song_id FROM songs WHERE title = 'Bleeding Love'), 'Closed off from love, I didn’t need the pain'),
(2, (SELECT song_id FROM songs WHERE title = 'Bleeding Love'), 'Once or twice was enough, and it was all in vain'),
(3, (SELECT song_id FROM songs WHERE title = 'Bleeding Love'), 'Time starts to pass, before you know it, you’re frozen'),
(4, (SELECT song_id FROM songs WHERE title = 'Bleeding Love'), 'But something happened for the very first time with you'),
(5, (SELECT song_id FROM songs WHERE title = 'Bleeding Love'), 'My heart melts into the ground, found something true'),
(1, (SELECT song_id FROM songs WHERE title = 'Meant to Be'), 'Baby, lay on back and relax'),
(2, (SELECT song_id FROM songs WHERE title = 'Meant to Be'), 'Kick your pretty feet up on my dash'),
(3, (SELECT song_id FROM songs WHERE title = 'Meant to Be'), 'No need to go nowhere fast'),
(4, (SELECT song_id FROM songs WHERE title = 'Meant to Be'), 'Let’s enjoy right here where we at'),
(5, (SELECT song_id FROM songs WHERE title = 'Meant to Be'), 'Who knows where this road is supposed to lead?'),
(1, (SELECT song_id FROM songs WHERE title = 'When I Was Your Man'), 'Same bed but it feels just a little bit bigger now'),
(2, (SELECT song_id FROM songs WHERE title = 'When I Was Your Man'), 'Our song on the radio, but it don’t sound the same'),
(3, (SELECT song_id FROM songs WHERE title = 'When I Was Your Man'), 'When our friends talk about you, all it does is just tear me down'),
(4, (SELECT song_id FROM songs WHERE title = 'When I Was Your Man'), 'Cause my heart breaks a little when I hear your name'),
(5, (SELECT song_id FROM songs WHERE title = 'When I Was Your Man'), 'It all just sounds like ooh, ooh, ooh, hoo');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Demons'), 'When the days are cold'),
(2, (SELECT song_id FROM songs WHERE title = 'Demons'), 'And the cards all fold'),
(3, (SELECT song_id FROM songs WHERE title = 'Demons'), 'And the saints we see are all made of gold'),
(4, (SELECT song_id FROM songs WHERE title = 'Demons'), 'When your dreams all fail'),
(5, (SELECT song_id FROM songs WHERE title = 'Demons'), 'And the ones we hail are the worst of all'),
(1, (SELECT song_id FROM songs WHERE title = 'Need You Now'), 'It’s a quarter after one, I’m all alone and I need you now'),
(2, (SELECT song_id FROM songs WHERE title = 'Need You Now'), 'Said I wouldn’t call, but I lost all control and I need you now'),
(3, (SELECT song_id FROM songs WHERE title = 'Need You Now'), 'And I don’t know how I can do without'),
(4, (SELECT song_id FROM songs WHERE title = 'Need You Now'), 'I just need you now'),
(5, (SELECT song_id FROM songs WHERE title = 'Need You Now'), 'Another shot of whiskey, can’t stop looking at the door'),
(1, (SELECT song_id FROM songs WHERE title = 'I Knew You Were Trouble'), 'I knew you were trouble when you walked in'),
(2, (SELECT song_id FROM songs WHERE title = 'I Knew You Were Trouble'), 'So shame on me now'),
(3, (SELECT song_id FROM songs WHERE title = 'I Knew You Were Trouble'), 'Flew me to places I’d never been'),
(4, (SELECT song_id FROM songs WHERE title = 'I Knew You Were Trouble'), 'Now I’m lying on the cold hard ground'),
(5, (SELECT song_id FROM songs WHERE title = 'I Knew You Were Trouble'), 'Oh, oh, trouble, trouble, trouble'),
(1, (SELECT song_id FROM songs WHERE title = 'Locked Out of Heaven'), 'Never had much faith in love or miracles'),
(2, (SELECT song_id FROM songs WHERE title = 'Locked Out of Heaven'), 'Never wanna put my heart on the line'),
(3, (SELECT song_id FROM songs WHERE title = 'Locked Out of Heaven'), 'But swimming in your water is something spiritual'),
(4, (SELECT song_id FROM songs WHERE title = 'Locked Out of Heaven'), 'I’m born again every time you spend the night'),
(5, (SELECT song_id FROM songs WHERE title = 'Locked Out of Heaven'), 'Cause your sex takes me to paradise'),
(1, (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 'I have died every day waiting for you'),
(2, (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 'Darling, don’t be afraid, I have loved you'),
(3, (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 'For a thousand years'),
(4, (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 'I’ll love you for a thousand more'),
(5, (SELECT song_id FROM songs WHERE title = 'A Thousand Years'), 'And all along, I believed I would find you');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Super Bass'), 'This one is for the boys with the booming system'),
(2, (SELECT song_id FROM songs WHERE title = 'Super Bass'), 'Top down, AC with the cooling system'),
(3, (SELECT song_id FROM songs WHERE title = 'Super Bass'), 'When he come up in the club, he be blazin'' up'),
(4, (SELECT song_id FROM songs WHERE title = 'Super Bass'), 'Got stacks on deck like he savin'' up'),
(5, (SELECT song_id FROM songs WHERE title = 'Super Bass'), 'And he ill, he real, he might gotta deal'),
(1, (SELECT song_id FROM songs WHERE title = 'I Write Sins Not Tragedies'), 'I chimed in with a, "Haven''t you people ever heard of'),
(2, (SELECT song_id FROM songs WHERE title = 'I Write Sins Not Tragedies'), 'Closing the goddamn door?!"'),
(3, (SELECT song_id FROM songs WHERE title = 'I Write Sins Not Tragedies'), 'No, it''s much better to face these kinds of things'),
(4, (SELECT song_id FROM songs WHERE title = 'I Write Sins Not Tragedies'), 'With a sense of poise and rationality'),
(5, (SELECT song_id FROM songs WHERE title = 'I Write Sins Not Tragedies'), 'I chimed in, "Haven''t you people ever heard of'),
(1, (SELECT song_id FROM songs WHERE title = 'Mr. Brightside'), 'Coming out of my cage and I''ve been doing just fine'),
(2, (SELECT song_id FROM songs WHERE title = 'Mr. Brightside'), 'Gotta, gotta be down because I want it all'),
(3, (SELECT song_id FROM songs WHERE title = 'Mr. Brightside'), 'It started out with a kiss, how did it end up like this?'),
(4, (SELECT song_id FROM songs WHERE title = 'Mr. Brightside'), 'It was only a kiss, it was only a kiss'),
(5, (SELECT song_id FROM songs WHERE title = 'Mr. Brightside'), 'Now I''m falling asleep and she''s calling a cab'),
(1, (SELECT song_id FROM songs WHERE title = 'Hook'), 'It doesn''t matter what I say'),
(2, (SELECT song_id FROM songs WHERE title = 'Hook'), 'So long as I sing with inflection'),
(3, (SELECT song_id FROM songs WHERE title = 'Hook'), 'That makes you feel that I''ll convey'),
(4, (SELECT song_id FROM songs WHERE title = 'Hook'), 'Some inner truth of vast reflection'),
(5, (SELECT song_id FROM songs WHERE title = 'Hook'), 'But I''ve said nothing so far'),
(1, (SELECT song_id FROM songs WHERE title = 'Semi-Charmed Life'), 'I want something else'),
(2, (SELECT song_id FROM songs WHERE title = 'Semi-Charmed Life'), 'I''m not listening when you say goodbye'),
(3, (SELECT song_id FROM songs WHERE title = 'Semi-Charmed Life'), 'The sky was gold, it was rose'),
(4, (SELECT song_id FROM songs WHERE title = 'Semi-Charmed Life'), 'I was taking sips of it through my nose'),
(5, (SELECT song_id FROM songs WHERE title = 'Semi-Charmed Life'), 'And I wish I could get back there');


INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'American Pie'), 'A long, long time ago'),
(2, (SELECT song_id FROM songs WHERE title = 'American Pie'), 'I can still remember how that music used to make me smile'),
(3, (SELECT song_id FROM songs WHERE title = 'American Pie'), 'And I knew if I had my chance'),
(4, (SELECT song_id FROM songs WHERE title = 'American Pie'), 'That I could make those people dance'),
(5, (SELECT song_id FROM songs WHERE title = 'American Pie'), 'And maybe they’d be happy for a while'),
(1, (SELECT song_id FROM songs WHERE title = 'We Didn''t Start the Fire'), 'We didn’t start the fire'),
(2, (SELECT song_id FROM songs WHERE title = 'We Didn''t Start the Fire'), 'It was always burning, since the world’s been turning'),
(3, (SELECT song_id FROM songs WHERE title = 'We Didn''t Start the Fire'), 'We didn’t start the fire'),
(4, (SELECT song_id FROM songs WHERE title = 'We Didn''t Start the Fire'), 'No, we didn’t light it, but we tried to fight it'),
(5, (SELECT song_id FROM songs WHERE title = 'We Didn''t Start the Fire'), 'Harry Truman, Doris Day, Red China, Johnnie Ray'),
(1, (SELECT song_id FROM songs WHERE title = 'Bohemian Rhapsody'), 'Is this the real life? Is this just fantasy?'),
(2, (SELECT song_id FROM songs WHERE title = 'Bohemian Rhapsody'), 'Caught in a landslide, no escape from reality'),
(3, (SELECT song_id FROM songs WHERE title = 'Bohemian Rhapsody'), 'Open your eyes, look up to the skies and see'),
(4, (SELECT song_id FROM songs WHERE title = 'Bohemian Rhapsody'), 'I’m just a poor boy, I need no sympathy'),
(5, (SELECT song_id FROM songs WHERE title = 'Bohemian Rhapsody'), 'Because I’m easy come, easy go, little high, little low'),
(1, (SELECT song_id FROM songs WHERE title = 'Basket Case'), 'Do you have the time to listen to me whine'),
(2, (SELECT song_id FROM songs WHERE title = 'Basket Case'), 'About nothing and everything all at once?'),
(3, (SELECT song_id FROM songs WHERE title = 'Basket Case'), 'I am one of those melodramatic fools'),
(4, (SELECT song_id FROM songs WHERE title = 'Basket Case'), 'Neurotic to the bone, no doubt about it'),
(5, (SELECT song_id FROM songs WHERE title = 'Basket Case'), 'Sometimes I give myself the creeps'),
(1, (SELECT song_id FROM songs WHERE title = 'The Man'), 'I know the score like the back of my hand'),
(2, (SELECT song_id FROM songs WHERE title = 'The Man'), 'Them other boys, I don’t give a damn'),
(3, (SELECT song_id FROM songs WHERE title = 'The Man'), 'They kiss on the ring, I carry the crown'),
(4, (SELECT song_id FROM songs WHERE title = 'The Man'), 'Nothing can break, nothing can break me down'),
(5, (SELECT song_id FROM songs WHERE title = 'The Man'), 'Don’t need no advice, I got a plan');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Rap God'), 'Look, I was gonna go easy on you not to hurt your feelings'),
(2, (SELECT song_id FROM songs WHERE title = 'Rap God'), 'But I’m only going to get this one chance'),
(3, (SELECT song_id FROM songs WHERE title = 'Rap God'), 'Something’s wrong, I can feel it'),
(4, (SELECT song_id FROM songs WHERE title = 'Rap God'), 'Just a feeling I’ve got, like something’s about to happen'),
(5, (SELECT song_id FROM songs WHERE title = 'Rap God'), 'But I don’t know what'),
(1, (SELECT song_id FROM songs WHERE title = 'Godzilla'), 'I can swallow a bottle of alcohol and I’ll feel like Godzilla'),
(2, (SELECT song_id FROM songs WHERE title = 'Godzilla'), 'Better hit the deck like the card dealer'),
(3, (SELECT song_id FROM songs WHERE title = 'Godzilla'), 'My whole squad’s in here, walking around the party'),
(4, (SELECT song_id FROM songs WHERE title = 'Godzilla'), 'A cross between a zombie apocalypse and B-Bobby'),
(5, (SELECT song_id FROM songs WHERE title = 'Godzilla'), 'The Brain Heenan which is probably the same reason'),
(1, (SELECT song_id FROM songs WHERE title = 'The Real Slim Shady'), 'May I have your attention, please?'),
(2, (SELECT song_id FROM songs WHERE title = 'The Real Slim Shady'), 'Will the real Slim Shady please stand up?'),
(3, (SELECT song_id FROM songs WHERE title = 'The Real Slim Shady'), 'I repeat, will the real Slim Shady please stand up?'),
(4, (SELECT song_id FROM songs WHERE title = 'The Real Slim Shady'), 'We’re gonna have a problem here'),
(5, (SELECT song_id FROM songs WHERE title = 'The Real Slim Shady'), 'Y’all act like you’ve never seen a white person before'),
(1, (SELECT song_id FROM songs WHERE title = 'It''s the End of the World as We Know It'), 'That’s great, it starts with an earthquake'),
(2, (SELECT song_id FROM songs WHERE title = 'It''s the End of the World as We Know It'), 'Birds and snakes, and aeroplanes'),
(3, (SELECT song_id FROM songs WHERE title = 'It''s the End of the World as We Know It'), 'Lenny Bruce is not afraid'),
(4, (SELECT song_id FROM songs WHERE title = 'It''s the End of the World as We Know It'), 'Eye of a hurricane, listen to yourself churn'),
(5, (SELECT song_id FROM songs WHERE title = 'It''s the End of the World as We Know It'), 'World serves its own needs, dummy, serve your own needs'),
(1, (SELECT song_id FROM songs WHERE title = 'Blinded by the Light'), 'Blinded by the light'),
(2, (SELECT song_id FROM songs WHERE title = 'Blinded by the Light'), 'Revved up like a deuce, another runner in the night'),
(3, (SELECT song_id FROM songs WHERE title = 'Blinded by the Light'), 'Blinded by the light'),
(4, (SELECT song_id FROM songs WHERE title = 'Blinded by the Light'), 'Revved up like a deuce, another runner in the night'),
(5, (SELECT song_id FROM songs WHERE title = 'Blinded by the Light'), 'Madman drummer bummers, Indians in the summer');

INSERT INTO lyrics (lyric_order_id, song_id, lyrics)
VALUES
(1, (SELECT song_id FROM songs WHERE title = 'Life Is a Highway'), 'Life is a highway'),
(2, (SELECT song_id FROM songs WHERE title = 'Life Is a Highway'), 'I wanna ride it all night long'),
(3, (SELECT song_id FROM songs WHERE title = 'Life Is a Highway'), 'If you’re going my way'),
(4, (SELECT song_id FROM songs WHERE title = 'Life Is a Highway'), 'I wanna drive it all night long'),
(5, (SELECT song_id FROM songs WHERE title = 'Life Is a Highway'), 'Through all these cities and all these towns'),
(1, (SELECT song_id FROM songs WHERE title = 'The Impression That I Get'), 'Have you ever been close to tragedy?'),
(2, (SELECT song_id FROM songs WHERE title = 'The Impression That I Get'), 'Or been close to folks who have?'),
(3, (SELECT song_id FROM songs WHERE title = 'The Impression That I Get'), 'Have you ever felt the pain so powerful'),
(4, (SELECT song_id FROM songs WHERE title = 'The Impression That I Get'), 'So heavy you collapse?'),
(5, (SELECT song_id FROM songs WHERE title = 'The Impression That I Get'), 'No? Well, I’ve never had to knock on wood'),
(1, (SELECT song_id FROM songs WHERE title = 'The Humpty Dance'), 'Alright, stop what you’re doing'),
(2, (SELECT song_id FROM songs WHERE title = 'The Humpty Dance'), 'Cause I’m about to ruin the image and the style that you’re used to'),
(3, (SELECT song_id FROM songs WHERE title = 'The Humpty Dance'), 'I look funny, but yo, I’m making money, see'),
(4, (SELECT song_id FROM songs WHERE title = 'The Humpty Dance'), 'So yo world, I hope you’re ready for me'),
(5, (SELECT song_id FROM songs WHERE title = 'The Humpty Dance'), 'Now gather around, I’m the new fool in town'),
(1, (SELECT song_id FROM songs WHERE title = 'One Week'), 'It’s been one week since you looked at me'),
(2, (SELECT song_id FROM songs WHERE title = 'One Week'), 'Cocked your head to the side and said, "I’m angry"'),
(3, (SELECT song_id FROM songs WHERE title = 'One Week'), 'Five days since you laughed at me'),
(4, (SELECT song_id FROM songs WHERE title = 'One Week'), 'Saying, "Get that together, come back and see me"'),
(5, (SELECT song_id FROM songs WHERE title = 'One Week'), 'Three days since the living room'),
(1, (SELECT song_id FROM songs WHERE title = 'End of the Line'), 'Well, it’s alright, riding around in the breeze'),
(2, (SELECT song_id FROM songs WHERE title = 'End of the Line'), 'Well, it’s alright, if you live the life you please'),
(3, (SELECT song_id FROM songs WHERE title = 'End of the Line'), 'Well, it’s alright, doing the best you can'),
(4, (SELECT song_id FROM songs WHERE title = 'End of the Line'), 'Well, it’s alright, as long as you lend a hand'),
(5, (SELECT song_id FROM songs WHERE title = 'End of the Line'), 'You can sit around and wait for the phone to ring');

