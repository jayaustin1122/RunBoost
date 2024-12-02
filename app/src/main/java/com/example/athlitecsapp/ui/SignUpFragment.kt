package com.example.athlitecsapp.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.athlitecsapp.R
import com.example.athlitecsapp.dao.UserDao
import com.example.athlitecsapp.database.RunBoostDatabase
import com.example.athlitecsapp.databinding.DialogLoginBinding
import com.example.athlitecsapp.databinding.FragmentSignUpBinding
import com.example.athlitecsapp.databinding.FragmentSplashBinding
import com.example.athlitecsapp.model.Routine
import com.example.athlitecsapp.table.Status
import com.example.athlitecsapp.table.User
import com.example.athlitecsapp.table.Workouts
import com.example.athlitecsapp.util.DialogUtils
import com.example.athlitecsapp.util.HomeFactory
import com.example.athlitecsapp.util.SignUpFactory
import com.example.athlitecsapp.viewmodels.HomeViewModel
import com.example.athlitecsapp.viewmodels.SignUpModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var userDao: UserDao
    private lateinit var database: RunBoostDatabase
    private lateinit var loadingDialog: SweetAlertDialog
    private val viewModel: HomeViewModel by viewModels { HomeFactory(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = RunBoostDatabase(requireContext())
        userDao = database.getUserDao()
        binding.apply {
            loginButton.setOnClickListener {
                validateInputs()
            }
        }
    }

    private fun validateInputs() {
        loadingDialog = DialogUtils.showLoading(requireActivity())
        loadingDialog.show()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) {
                binding.emailEditText.error = "Username is required"
                loadingDialog.dismiss()
            }
            if (password.isEmpty()) {
                binding.passwordEditText.error = "Password is required"
                loadingDialog.dismiss()
            }
        } else {
            viewModel.insertUser(User(email = email, password = password))
            viewModel.insertRoutine(routinesLists)
            viewModel.insertWorkouts(workouts)
            viewModel.insertStatus(
                Status(
                    level1 = 1,
                    level2 = 1,
                    level3 = 1,
                    level4 = 1,
                    level5 = 1,
                    level6 = 1,
                    level7 = 1,
                    level1DayProgress = 1,
                    level2DayProgress = 1,
                    level3DayProgress = 1,
                    level4DayProgress = 1,
                    level5DayProgress = 1,
                    level6DayProgress = 1,
                    level7DayProgress = 1,
                )
            )

            Log.d("LoginFragment", "User inserted successfully: $email")
            findNavController().navigate(R.id.signInFragment)
            loadingDialog.dismiss()
            DialogUtils.showSuccessMessage(
                requireActivity(),
                "Success",
                "Account created successfully"
            ).show()

        }
    }


    private val routinesLists = listOf(
        Routine(
            1,
            "COOL DOWN EXERCISE",
            """Child’s Pose
- Purpose: To stretch the spine, hips, and lower back muscles, promoting
relaxation.
- Description: Start by kneeling on the floor, with knees apart and big toes
touching. Extend your arms forward, allowing your chest to sink down to the
ground and forehead to rest on the floor. Hold and breathe deeply.
Right Hip Stretch (Leg Down)
- Purpose: To loosen tightness in the right hip and reduce tension in the hip
flexors.
- Description: Begin by kneeling, then extend your left leg back while keeping
your right leg bent at a 90-degree angle in front of you. Lean forward slightly to
deepen the stretch.
Right Hip Stretch (Leg Up)
- Purpose: To open up the hip flexors and increase range of motion in the hip.
- Description: In the previous position, lift your back knee off the floor and press
through the toes of the left leg to intensify the stretch.
Left Hip Stretch (Leg Down)
- Purpose: To stretch the left hip and relieve any tightness in the hip muscles.
- Description: Repeat the right hip stretch on the left side, extending your right leg
back and keeping the left leg bent in front.
Left Hip Stretch (Leg Up)
- Purpose: To stretch the left hip more deeply and engage the hip flexors.
- Description: In the left hip stretch position, lift the right knee off the floor,
pressing through your back toes.
Right Deep Lunge
- Purpose: To lengthen the hip flexors and strengthen the hamstrings and glutes.
- Description: From a standing position, take a large step forward with your right
leg, bending it into a lunge while keeping the back leg extended. Lower your hips
and hold.
Left Deep Lunge
- Purpose: Similar to the right deep lunge, to improve hip flexibility and leg
strength.
- Description: Step forward with the left leg, lowering into a lunge position and
extending the right leg back.
Butterfly
- Purpose: To open the hips and stretch the inner thighs.
- Description: Sit on the floor, bring the soles of your feet together, and let your
knees fall out to the sides. Hold your feet and gently press your knees down.
Right Lying Knee Tuck
- Purpose: To release tension in the lower back and glute muscles.
- Description: Lie on your back and pull your right knee towards your chest,
keeping the left leg extended.
Left Lying Knee Tuck
- Purpose: Similar to the right knee tuck, to relieve tension in the lower back and
glutes.
- Description: Lie on your back, pulling the left knee towards your chest while
keeping the right leg extended.
Right Lying Leg Over
- Purpose: To stretch the lower back and improve spinal mobility.
- Description: Lie on your back, extend your right arm out, and cross your right
knee over to the left side. Keep your shoulders on the ground.
Left Lying Leg Over
- Purpose: Similar to the right lying leg over, for lower back relief and spinal
flexibility.
- Description: Extend your left arm and cross your left knee over to the right side.
Seated Legs Apart Middle Reach
- Purpose: To stretch the hamstrings, inner thighs, and lower back.
- Description: Sit with legs extended out in a “V” shape. Reach forward towards
the center, keeping your back straight.
Lying Right Side Quad Stretch
- Purpose: To release tension in the right quadriceps and improve knee flexibility.
- Description: Lie on your left side, pull your right foot towards your glutes, and
hold your ankle.
Lying Left Side Quad Stretch
- Purpose: Similar to the right side quad stretch, to loosen the left quadriceps.
- Description: Lie on your right side, pull your left foot toward your glutes, and
hold.
Seated Right Knee Over Tuck
- Purpose: To stretch the glutes and outer hip muscles.
- Description: Sit with your left leg extended and cross your right knee over the
left leg, pulling it closer to your chest.
Seated Right Knee Over Twist
- Purpose: To stretch the spine and improve hip flexibility.
- Description: From the seated right knee tuck, twist your torso to the right,
placing your left elbow outside your right knee.
Seated Left Knee Over Tuck
- Purpose: To release tension in the glutes and outer hip on the left side.
- Description: With your right leg extended, cross your left knee over, pulling it
towards your chest.
Seated Left Knee Over Twist
- Purpose: Similar to the right twist, to stretch the spine and hips.
- Description: Twist your torso to the left, using your right elbow to press against
your left knee.
Right Angled Calf Stretch
- Purpose: To stretch the calf muscles and Achilles tendon.
- Description: Stand facing a wall, place your right foot behind at an angle, and
press your heel down.
Left Angled Calf Stretch
- Purpose: Similar to the right calf stretch, to loosen the left calf.
- Description: Place your left foot at an angle behind you, pressing your heel
down.
Standing Legs Apart Right Reach
- Purpose: To stretch the hamstrings, side body, and hip on the right side.
- Description: Stand with feet wide apart, reach down towards your right foot,
keeping the left arm extended.
Standing Legs Apart Left Reach
- Purpose: To stretch the left hamstring, hip, and side.
- Description: With feet wide apart, reach down towards your left foot, extending
your right arm overhead.
Feet Together Reach Pull
- Purpose: To stretch the hamstrings and lower back.
- Description: Stand with feet together, reach down towards your toes, and hold.
Standing Right Quad Stretch
- Purpose: To stretch the right quadriceps and improve flexibility in the thigh.
- Description: Stand on the left leg, pull your right foot towards your glutes, and
hold.
Standing Left Quad Stretch
- Purpose: To stretch the left quadriceps.
- Description: Stand on the right leg, pull your left foot towards your glutes.
Left Arm Over
- Purpose: To stretch the shoulder and upper arm.
- Description: Extend your left arm across your chest, using the right hand to
press it closer.
Right Arm Over
- Purpose: Similar to the left arm over, to stretch the right shoulder and upper
arm.
- Description: Extend your right arm across your chest and use your left hand to
press it closer.
Right Arm Behind
- Purpose: To stretch the triceps and upper back.
- Description: Lift your right arm overhead, bend at the elbow, and gently press it
down with the left hand.
Left Arm Behind
- Purpose: Similar to the right arm behind, to stretch the left triceps.
- Description: Lift your left arm overhead, bend it, and gently press it with your
right hand.
Right Lat Stretch
- Purpose: To stretch the lats and increase side body flexibility.
- Description: Extend your right arm overhead and lean to the left side, stretching
along the right side.
Left Lat Stretch
- Purpose: Similar to the right lat stretch, to stretch the left side.
- **Description**: Extend your left arm and lean to the right side.
Arm Front Push
- Purpose: To stretch the forearm and wrist extensors.
- Description: Extend your right arm in front, pointing fingers down, and use your
left hand to pull the fingers back.
Arms Behind Butt Push
- Purpose: To stretch the chest, shoulders, and upper arms.
- Description: Stand with feet shoulder-width apart, clasp your hands behind your
back, and gently lift your arms for a stretch.""",
            "Cooldown",
            R.raw.cooldown,
            null,
            false,
        ),
        Routine(
            2,
            "Angkling (Ankle Hops)",
            """ - Description: Stand with feet hip-width apart, then quickly hop on the balls
of your feet while keeping your knees slightly bent. Keep your ankles active
and your body upright.
 - Purpose: Strengthens the calves, improves ankle stability, and helps with
proper foot placement during running. It also aids in increasing stride
frequency and agility.
""",
            "Running Drills",
            R.raw.angkling,
            null,
            false,
        ),
        Routine(
            3,
            "A-Skip",
            """ - Description: While jogging, skip forward by driving one knee up toward
your chest. The other leg remains extended straight behind you as you
switch legs in a fluid motion. Focus on controlled, rhythmic movements with
a slight bounce.
 - Purpose: Improves knee drive, coordination, and running posture. It
enhances the mechanics of the stride and helps develop efficient movement
patterns, especially in the lower body.
""",
            "Running Drills",
            R.raw.askips,
            null,
            false,
        ),
        Routine(
            4,
            "B-Skip",
            """- Description: Similar to A-skip, but after driving your knee up, extend your
leg forward, then quickly bring it down to land under your body. Focus on
controlling your movements and having an extended leg in front of you.
 - Purpose: Increases stride length and encourages proper leg extension.
This drill enhances running efficiency by developing strong hip flexors,
improving overall running mechanics.""",
            "",
            R.raw.bskips,
            null,
            false,
        ),

        Routine(
            5,
            "C-Skip",
            """- Description: Begin by skipping forward with one knee raised. After the
knee drive, extend the lower leg outward in a circular motion before returning
it to the ground. The opposite leg remains extended straight behind you.
 - Purpose- Improves hip mobility and strength, as well as balance. It helps
activate the glutes and hamstrings and develops a powerful stride with an
efficient leg cycle during running.
""",
            "Running Drills",
            R.raw.cskips,
            null,
            false,
        ),
        Routine(
            6,
            "Butt Kick",
            """- Description: While jogging, kick your heels up toward your glutes. Ensure
your knees are pointed straight down, and your feet don’t travel too far
forward. Keep your posture upright.
 - Purpose: Strengthens the hamstrings and improves running posture. This
drill helps increase stride frequency and aids in the proper mechanics of the
leg swing during running.""",
            "Running Drills",
            R.raw.highkneebutkicks,
            null,
            false,
        ),

        Routine(
            7,
            "Russian (Footwork Drill)",
            """ - Description:
 The Russian footwork drill is a lateral movement drill that involves quick,
side-to-side shuffling. Start in a semi-squat position with your knees bent and
feet about shoulder-width apart. Step one foot to the side, then quickly bring
the other foot to meet it. Continue this side-to-side shuffle for a set period of
time or distance, focusing on fast foot movement and minimal ground
contact.

 - Purpose:
 This drill improves **agility**, **lateral movement**, and **foot speed**. It
helps runners develop better control and coordination during quick changes
in direction, which is essential for improving running efficiency and
preventing injury. The drill also strengthens the calves, quads, and hip
muscles while encouraging proper foot placement and balance during lateral
movements.""",
            "Running Drills",
            R.raw.russian,
            null,
            false,
        ),
        Routine(
            8,
            "High Knees",
            """- Description: While jogging, lift your knees up as high as possible toward
your chest. Focus on keeping your chest upright and avoiding leaning
forward. Alternate legs in a quick and controlled motion.
 - Purpose: Improves knee drive, core stability, and leg coordination. High
knees help to develop a faster cadence, which is important for efficient
running mechanics.""",
            "Running Drills",
            R.raw.highknees,
            null,
            false,
        ),
        Routine(
            9,
            " Bounding",
            """ - Description: Leap forward with an exaggerated stride, driving one knee
up while the opposite leg extends behind. Push off the ground with power to
generate height and distance with each bound.
 - Purpose: Builds explosive power and strength in the legs, particularly in
the glutes, hamstrings, and calves. Bounding improves stride length, running
power, and helps develop overall running form and technique.""",
            "Running Drills",
            R.raw.highkneebutkicks,
            null,
            false,
        ),
        Routine(
            10,
            " Carioca",
            """- Description: Perform a lateral movement by crossing one leg over the
other while maintaining a quick pace. Alternate sides, crossing one leg in
front and then behind as you move sideways.
 - Purpose: Enhances agility, lateral movement, and coordination. This drill
helps improve hip mobility and strengthens the muscles involved in lateral
movements, which are essential for overall running efficiency and injury
prevention.
""",
            "Running Drills",
            R.raw.carioca,
            null,
            false,
        ),
        Routine(
            11,
            "High Knee Extension",
            """ - Description: While jogging, drive your knees high toward your chest.
When your knee reaches the highest point, extend it forward before bringing
it back down to the ground. Alternate legs.
 - Purpose: Improves flexibility, hip flexor strength, and knee drive. This drill
helps with the extension phase of the running stride and aids in increasing
stride length for faster running.
""",
            "Running Drills",
            R.raw.highkneewithextension,
            null,
            false,
        ),
        Routine(
            12,
            " Back Kick",
            """- Description: While jogging, kick your heels back toward your glutes. Keep
your knees aligned and avoid twisting the legs. Focus on engaging the
hamstrings during the movement.
 - Purpose: Strengthens the hamstrings and glutes, helping to improve
stride power and running efficiency. It also aids in achieving a fluid leg cycle
while reducing the risk of overstriding.""",
            "Running Drills",
            R.raw.bkick,
            null,
            false,
        ),
        Routine(
            13,
            "Kangaroo Hops",
            """ - Description: Hop forward with both feet together, focusing on height and
distance. Keep the knees slightly bent to absorb impact and maintain a
smooth, spring-like motion.
 - Purpose: Builds lower-body power and explosiveness, particularly in the
calves, quads, and glutes. This drill improves the ability to generate force
quickly, which is crucial for sprinting and overall running performance.""",
            "Running Drills",
            R.raw.kangaroo,
            null,
            false,
        ),


        //abdominal
        Routine(
            14,
            "Push-up",
            """A push-up is a bodyweight exercise that builds upper body and core strength by engaging muscles in the chest, shoulders, triceps, and abdomen. It’s a classic and versatile movement that requires no equipment, making it ideal for workouts anywhere. Here’s a step-by-step guide on how to perform a proper push-up:
Steps to Do a Push-Up
1.	Start in a Plank Position: Begin with your hands placed flat on the floor, slightly wider than shoulder-width apart. Keep your legs extended straight behind you, feet together, and your body in a straight line from head to heels. Engage your core to keep your back from sagging.
2.	Lower Your Body: Slowly bend your elbows, lowering your body toward the floor. Keep your elbows close to your body (at about a 45-degree angle) to protect your shoulders. Lower yourself until your chest is close to or lightly touches the floor.
3.	Push Back Up: Press through your palms, straightening your arms to lift your body back to the starting position. Keep your core tight and avoid arching your back as you push up.
4.	Repeat: Continue lowering and pushing up for the desired number of repetitions, maintaining a controlled, steady pace.
Tips for Good Form
Keep your head in line with your spine; don’t let it droop.
Keep your elbows slightly tucked in instead of flaring them out.
Maintain a straight, stable body position throughout each repetition.
Push-ups can be modified to increase or decrease difficulty. Beginners might start with knee push-ups or incline push-ups, while advanced exercisers can try decline or one-arm push-ups.
""",
            "Arm",
            null,
            R.drawable.pushup,
            false,
        ),
        Routine(
            15,
            "Knee push-up",
            """A knee push-up is a modified version of the traditional push-up designed to make the exercise more accessible. It provides similar benefits—targeting the chest, shoulders, triceps, and core—but reduces the load by allowing the knees to rest on the floor. This modification is excellent for beginners or for anyone building upper body strength and working up to a standard push-up.
Steps to Do a Knee Push-Up
1.	Position Yourself: Start by getting on your hands and knees. Place your hands slightly wider than shoulder-width apart, and position your knees behind you so your body forms a straight line from your head to your knees. Engage your core to keep your back flat.
2.	Lower Your Body: Bend your elbows, lowering your chest toward the floor. Keep your elbows at a 45-degree angle from your body to protect your shoulders. Lower yourself until your chest Is close to or lightly touches the floor.
3.	Push Back Up: Press through your hands to straighten your arms, lifting your chest back up to the starting position. Maintain your body alignment and keep your core engaged as you push.
4.	Repeat: Continue to lower and lift for the desired number of repetitions, keeping a steady, controlled pace.
Tips for Good Form
Keep your body in a straight line from head to knees; avoid bending at the hips or letting your lower back sag.
Keep your elbows slightly tucked in and avoid letting them flare outward.
Focus on a slow, controlled movement to maximize the workout’s effectiveness.
The knee push-up is a great stepping stone for mastering the full push-up and can also be a useful exercise for warming up or during recovery.
""",
            "Arm",
            null,
            R.drawable.kneepushup,
            false,
        ),

        Routine(
            16,
            "Plank",
            """A plank is a bodyweight exercise that targets the core muscles, while also engaging the shoulders, back, and glutes to help stabilize the body. It is an isometric exercise, meaning you hold a position without movement, which builds endurance and strength in the core and improves overall stability. Planks are a versatile exercise that can be modified for different fitness levels.
Steps to Do a Plank
1.	Start in a Forearm or High Plank Position:
For a forearm plank: Place your forearms on the floor, elbows directly under your shoulders, and hands flat or clasped together.
For a high plank: Place your hands flat on the floor, directly under your shoulders, as if in the top position of a push-up.
2.	Extend Your Legs: Step both feet back, extending your legs straight behind you so your body forms a straight line from your head to your heels.
3.	Engage Your Core and Glutes: Tighten your abdominal muscles to prevent your lower back from sagging, and squeeze your glutes to keep your hips level. Avoid lifting your hips too high.
4.	Hold the Position: Keep your body steady and aligned, holding the position for a set time (typically 20 to 60 seconds for beginners). Focus on breathing evenly and maintaining good form.
5.	Release: When you’ve completed your hold, gently lower your knees to the floor to come out of the plank position.
Tips for Good Form
Keep your neck in a neutral position by looking down at the floor.
Avoid letting your hips sag or raise too high; your body should remain in a straight line.
Focus on engaging your core, glutes, and leg muscles to maintain stability.
Planks are an effective core exercise and can be modified to increase or decrease difficulty, such as by lifting one arm or leg, performing side planks, or incorporating movement.
""",
            "Arm",
            null,
            R.drawable.plank,
            false,
        ),
        Routine(
            17,
            "Side plank",
            """A side plank is a variation of the plank exercise that specifically targets the oblique muscles on the sides of your core, while also working your shoulders, glutes, and legs for stability. It is an excellent exercise for building core strength, improving balance, and enhancing overall stability.
Steps to Do a Side Plank
1.	Start on Your Side: Lie on your side with your legs extended straight, stacking your feet on top of each other. Place your forearm on the floor, elbow directly under your shoulder, and keep your forearm perpendicular to your body.
2.	Lift Your Hips: Engage your core and lift your hips off the floor, forming a straight line from your head to your heels. Keep your body in a single, aligned position without letting your hips drop.
3.	Raise Your Free Arm (Optional): For added stability, you can lift your top arm straight up toward the ceiling, aligning it with your shoulder. Alternatively, place it on your hip if that’s more comfortable.
4.	Hold the Position: Maintain this position for a set amount of time, typically starting with 15-30 seconds for beginners, focusing on keeping your core engaged and your body aligned.
5.	Switch Sides: Gently lower your hips back to the floor, then repeat the side plank on the opposite side.
Tips for Good Form
Keep your head in line with your spine and avoid letting your head drop.
Engage your core and glutes to maintain stability.
Avoid letting your hips sag or twist; focus on maintaining a straight line.
Side planks can be modified by bending the bottom knee and placing it on the floor for support, making the exercise easier. Advanced variations include lifting the top leg or incorporating a weight for added resistance.
""",
            "Arm",
            null,
            R.drawable.sdieplank,
            false,
        ),
        Routine(
            18,
            "Back plank",
            """A back plank, also known as a reverse plank, is a core exercise that targets the posterior chain, working muscles along the back of the body including the glutes, hamstrings, lower back, and shoulders. Unlike a traditional plank, a back plank emphasizes the backside of the body, improving posture and building overall core strength.
Steps to Do a Back Plank
1.	Start in a Seated Position: Sit on the floor with your legs extended straight out in front of you and place your hands on the floor just behind your hips. Your fingers should point toward your feet, but if this feels uncomfortable, you can point them outward.
2.	Engage Your Core and Lift Your Hips: Press through your hands and heels as you lift your hips off the floor. Your body should form a straight line from your head to your heels. Squeeze your glutes and engage your core to maintain this alignment.
3.	Position Your Head and Gaze: Keep your head in a neutral position by looking up or slightly forward, depending on your comfort. Avoid letting your head drop back or forward.
4.	Hold the Position: Maintain the back plank position for a set amount of time, such as 15-30 seconds to start. Focus on keeping your hips lifted and your core engaged without letting your lower back sag.
5.	Lower Yourself Down: Slowly lower your hips back to the ground to release the position, then repeat if desired.
Tips for Good Form
Keep your body in a straight line, without letting your hips sag or rise too high.
Engage your glutes and core muscles to stabilize your body.
Avoid locking out your elbows completely; keep a slight bend to reduce stress on the joints.
The back plank can be modified by bending the knees if a full back plank is too challenging. Advanced variations include lifting one leg or adding ankle weights for increased resistance. This exercise is great for building core and posterior strength, which supports overall balance and posture.
""",
            "Arm",
            null,
            R.drawable.backplank,
            false,
        ),
        Routine(
            19,
            "Superman plank",
            """The Superman plank is an advanced core exercise that combines elements of the plank with the Superman exercise to engage the core, glutes, shoulders, and lower back. It involves lifting opposite arm and leg while holding a plank position, which challenges balance and stability, making it an effective move for improving core strength and coordination.
Steps to Do a Superman Plank
1.	Start in a High Plank Position: Begin with your hands placed directly under your shoulders and your legs extended behind you. Your body should form a straight line from head to heels, and your core should be engaged.
2.	Lift Opposite Arm and Leg: Slowly lift your right arm and left leg off the floor simultaneously, extending them straight out in front and behind you. Try to keep your hips and shoulders square to the ground without twisting.
3.	Hold the Position: Hold this extended position briefly (about 2-3 seconds), focusing on maintaining balance and keeping your core engaged to avoid tilting or sagging.
4.	Lower and Switch Sides: Slowly lower your arm and leg back to the plank position, then lift your left arm and right leg to repeat on the other side.
5.	Repeat: Continue alternating sides for a desired number of repetitions, keeping movements slow and controlled.
Tips for Good Form
Keep your core tight to prevent your hips from sagging or rotating.
Focus on slow, controlled movements rather than speed to maintain balance.
Keep your gaze slightly forward or down to maintain neck alignment.
The Superman plank is a challenging exercise that requires good core stability and balance. Beginners may start by lifting only the arms or legs separately before progressing to lifting both. This exercise is excellent for targeting multiple core muscles and improving overall body control.
""",
            "Arm",
            null,
            R.drawable.superman,
            false,
        ),
        Routine(
            20,
            "Push-up(Full)",
            """The press-up or the push-up exercise is a very popular exercise used in upper extremity training. It is a closed kinetic chain exercise that requires no tools and uses the body weight for resistance[1]. It has many variations, so it can be adjusted according to the fitness level. They work the triceps, pectoral muscles, and shoulders. Using proper form, they can also strengthen the lower back and core by engaging (pulling in) the abdominal muscles. Push-ups are a fast and effective exercise for building strength.""",
            "Arm",
            null,
            R.drawable.pushup,
            false,
        ),

        Routine(
            22,
            "Bridge",
            """A bridge is a bodyweight exercise that primarily targets the glutes, lower back, and core, while also engaging the hamstrings and thighs. It is a simple but effective exercise for improving core stability, strengthening the posterior chain, and enhancing hip mobility. The bridge can also help improve posture by strengthening the lower back and glutes.
Steps to Do a Bridge
1.	Start on Your Back: Lie flat on your back with your knees bent and feet flat on the floor, hip-width apart. Place your arms at your sides with your palms facing down.
2.	Engage Your Core: Tighten your core muscles and press your feet into the floor to prepare for the lift.
3.	Lift Your Hips: Push through your heels and lift your hips off the ground, squeezing your glutes as you raise your hips towards the ceiling. Your body should form a straight line from your shoulders to your knees at the top of the movement.
4.	Hold the Position: At the top of the movement, hold for 2-3 seconds while maintaining tension in your glutes and core. Make sure not to arch your back excessively.
5.	Lower Your Hips: Slowly lower your hips back to the starting position, keeping control throughout the movement.
6.	Repeat: Perform the desired number of repetitions, focusing on using your glutes and core muscles to lift your hips, not just your lower back.
Tips for Good Form
Keep your feet flat on the floor and avoid letting your knees splay outward.
Engage your core throughout the movement to protect your lower back.
Avoid overextending at the top; you don’t need to lift your hips higher than your shoulders and knees.
Make sure your body forms a straight line at the top, with no arching in the lower back.
Variations:
Single-Leg Bridge: Lift one leg off the floor while holding the bridge position, increasing the difficulty by targeting one glute at a time.
Bridge with Leg Extensions: Add leg extensions at the top of the bridge to engage the lower body more intensely.
The bridge is a great exercise for beginners and can be modified to increase difficulty as strength improves. It’s effective for strengthening the glutes, lower back, and core, and improving overall functional movement.
""",
            "Abdominal",
            null,
            R.drawable.bridgeabdominal,
            false,
        ),
        Routine(
            23,
            "Sit-ups (full)",
            """A full sit-up is a classic abdominal exercise that strengthens the core, specifically targeting the rectus abdominis (the “six-pack” muscles), as well as the hip flexors, lower back, and even the obliques. Unlike crunches, which involve only a partial flexion of the torso, a full sit-up requires you to lift your entire back off the floor and return to a seated position.
Steps to Do a Full Sit-Up
1.	Start in a Lying Position: Lie flat on your back with your knees bent and feet flat on the floor, about hip-width apart. Place your hands behind your head (or cross them over your chest, depending on your preference) to support your neck. Keep your elbows wide to avoid pulling on your head or neck.
2.	Engage Your Core: Tighten your abdominal muscles and press your lower back into the floor. This will help activate your core before the movement begins.
3.	Lift Your Upper Body: Using your core muscles, lift your torso toward your knees, aiming to sit all the way up. As you rise, keep your feet pressed into the floor and avoid using momentum or jerking your body. Focus on using your abs to lift.
4.	Sit Up Fully: Continue lifting until your chest is near your thighs and your torso is fully upright. Be mindful not to use your arms to pull yourself up; instead, rely on your core strength.
5.	Lower Back Down: Slowly lower your torso back to the floor in a controlled manner, vertebra by vertebra, until you’re back to the starting position. Avoid letting your back hit the ground with a thud to keep tension in your abs.
6.	Repeat: Perform the desired number of repetitions, focusing on controlled movements rather than rushing through the exercise.
Tips for Good Form
Do not pull on your neck: Keep your hands lightly supporting your head or crossed on your chest to avoid straining your neck muscles.
Engage your core: Focus on using your abdominal muscles to lift your torso rather than relying on your arms or legs.
Move slowly: Perform the sit-up in a slow, controlled manner to maximize the engagement of the core muscles and avoid using momentum.
Keep your feet anchored: Ensure your feet stay flat on the floor during the movement. If needed, you can ask a partner to hold your feet for added stability.
Variations:
Crunches: A smaller, more controlled movement where only the shoulders and upper back lift off the ground.
Weighted Sit-Ups: Hold a weight (like a medicine ball or dumbbell) on your chest to increase resistance.
V-Sit-Ups: Lift both your legs and your upper body simultaneously, creating a “V” shape with your body for a more advanced variation.
Full sit-ups are a great exercise for building core strength and improving endurance, but they can put strain on the lower back if done incorrectly. For those with lower back issues, it’s often recommended to focus on safer core exercises, like crunches or planks, to avoid unnecessary stress on the spine.
""",
            "Abdominal",
            R.raw.situpfull,
            null,
            false,
        ),
        Routine(
            24,
            "Sit-ups (half)",
            """A half sit-up is a variation of the traditional sit-up that focuses on engaging the core muscles, particularly the abdominal muscles, but involves a smaller range of motion. In a half sit-up, the torso only lifts halfway up instead of all the way to a seated position, making it a more accessible option for beginners or those who want to reduce strain on the lower back.
Steps to Do a Half Sit-Up
1.	Start in a Lying Position: Lie on your back with your knees bent and feet flat on the floor, about hip-width apart. Place your hands lightly behind your head (or cross them over your chest, depending on your preference).
2.	Engage Your Core: Tighten your abdominal muscles and press your lower back gently into the floor. This prepares your core to help lift your upper body.
3.	Lift Your Upper Body: Using your abdominal muscles, slowly lift your head, neck, and shoulders off the floor. Aim to raise your torso just until your upper back is off the ground—this is the “half” part of the sit-up.
4.	Hold and Lower Back Down: Hold the raised position briefly, engaging your core. Then, slowly lower your torso back to the floor in a controlled manner.
5.	Repeat: Perform the desired number of repetitions, keeping your movements slow and focused on using your core rather than momentum.
Tips for Good Form
Don’t pull on your neck: Avoid using your hands to pull your head or neck forward. Instead, keep your hands lightly supporting your head or resting on your chest.
Engage your core: Focus on using your abdominal muscles to lift your upper body, rather than relying on your arms or legs.
Controlled movement: Perform each half sit-up slowly and in a controlled manner, especially as you lower yourself back to the ground.
Avoid arching your back: Keep your lower back pressed into the floor and maintain a slight natural curve in your spine.
Benefits and Tips:
Lower Impact: The half sit-up is less intense than a full sit-up, which makes it easier on the lower back and more accessible for beginners or people with limited flexibility.
Focus on Core Activation: Although it’s less demanding than a full sit-up, the half sit-up still effectively engages the core muscles and helps improve abdominal strength.
Variations:
Crunches: A shorter version of the half sit-up where only the upper part of the torso is lifted, targeting the upper abs.
Knee or Hip Flexor Engagement: To make the exercise slightly more challenging, you can cross your arms over your chest or place a weight on your chest to engage the abs more fully.
The half sit-up is a great way to build core strength while minimizing strain on the lower back, and it can be a good starting point for progressing to more advanced core exercises.
""",
            "Abdominal",
            R.raw.situphalf,
            null,
            false,
        ),
        Routine(
            25,
            "Vertical leg crunch",
            """A vertical leg crunch is an advanced variation of the traditional crunch that targets the upper abs and hip flexors while also engaging the lower abs and obliques. By elevating the legs and performing a crunch, the exercise increases the intensity and demands greater core activation, making it a great choice for building abdominal strength and definition.
Steps to Do a Vertical Leg Crunch
1.	Start in a Lying Position: Lie flat on your back on a mat, with your legs extended straight up toward the ceiling, keeping your feet flexed. Place your hands lightly behind your head, elbows pointing out to the sides. Alternatively, you can cross your arms over your chest.
2.	Engage Your Core: Tighten your abdominal muscles and press your lower back gently into the floor. This helps protect your spine and activates your core before you begin the movement.
3.	Lift Your Upper Body: Using your abdominal muscles, lift your upper body (head, neck, and shoulders) off the floor, bringing your chest toward your legs. Focus on using your abs to raise your torso, not your neck or arms.
4.	Crunch Toward Your Legs: As you lift your upper body, try to touch your elbows to your thighs or reach your chest toward your knees. Keep your legs extended upward throughout the movement.
5.	Lower Back Down: Slowly lower your torso back to the starting position in a controlled manner, keeping tension in your abs as you descend.
6.	Repeat: Perform the desired number of repetitions, focusing on slow and controlled movements to maximize core engagement.
Tips for Good Form
Don’t pull on your neck: Keep your hands lightly supporting your head, but avoid using them to pull your neck forward. This can cause strain on the neck muscles.
Use your core: Focus on engaging your abdominal muscles to lift your torso. Keep your legs straight and avoid using momentum to complete the movement.
Controlled movements: Perform each crunch slowly, particularly as you lower your torso back to the ground, to maintain control and increase the effectiveness of the exercise.
Benefits of Vertical Leg Crunches
Targets the Upper and Lower Abs: Elevating the legs while performing the crunch forces the lower abs to engage more, making it a more challenging exercise than traditional crunches.
Engages Hip Flexors: Keeping the legs vertical adds an element that works the hip flexors, which are often neglected in many core exercises.
Improves Flexibility and Stability: Holding the legs up in a vertical position requires flexibility and stability, which further challenges the core.
Variations:
Add Weight: For increased intensity, you can hold a weight or medicine ball between your feet or on your chest to add resistance.
Bent Knee Vertical Crunch: If straight legs are too challenging, you can bend your knees slightly or keep them slightly lower than the vertical position.
The vertical leg crunch is an effective exercise to build a strong, toned core, but it requires proper form to avoid neck strain or lower back discomfort. Starting slowly and focusing on controlled movements will maximize its benefits.
""",
            "Abdominal",
            R.raw.verticallegcrunch,
            null,
            false,
        ),
        Routine(
            26,
            "Reverse crunch",
            """A reverse crunch is an abdominal exercise that primarily targets the lower part of the core, particularly the lower abs and hip flexors. Unlike traditional crunches, which involve lifting the upper body, reverse crunches involve lifting the lower body by engaging the lower abdominal muscles. This exercise is highly effective for strengthening the core and improving overall abdominal muscle definition.
Steps to Do a Reverse Crunch
1.	Start in a Lying Position: Lie flat on your back on a mat with your legs bent at a 90-degree angle. Keep your feet flat on the floor and place your hands by your sides or under your hips for support. You can also place your hands behind your head if preferred, but be mindful to not use them to pull your neck.
2.	Engage Your Core: Tighten your abdominal muscles and press your lower back gently into the floor. This helps activate your core and stabilize your pelvis before beginning the movement.
3.	Lift Your Legs: Lift your legs off the ground, keeping your knees bent at a 90-degree angle. Your thighs should be vertical to the floor, and your feet should be about a foot above the ground.
4.	Curl Your Hips and Lower Body: Engage your lower abs and use your core to pull your knees toward your chest. As you do this, lift your hips off the ground slightly, curling your pelvis upward. Focus on using your abdominal muscles rather than swinging your legs.
5.	Lower Slowly: Slowly lower your legs back to the starting position without letting your feet touch the ground. Keep tension in your core throughout the movement.
6.	Repeat: Perform the desired number of repetitions, focusing on controlled movements rather than fast, jerky motions.
Tips for Good Form
Don’t use momentum: Focus on controlled movements and avoid swinging your legs to lift your hips. Using momentum can reduce the effectiveness of the exercise and increase the risk of injury.
Avoid straining your neck: Keep your head resting on the mat. If you place your hands behind your head, don’t pull on your neck.
Engage your core: Keep your core tight and focus on using your lower abs to lift your hips, not your legs or back.
Mind the lower back: Keep your lower back pressed into the mat during the movement to avoid strain on the spine.
Benefits of Reverse Crunches
Targets Lower Abs: Reverse crunches are particularly effective for working the lower part of the abdominal muscles, an area that can be difficult to target with traditional crunches.
Improves Core Strength: This exercise helps strengthen the entire core, including the lower abs, obliques, and hip flexors.
Minimal Strain on Neck and Upper Back: Unlike traditional crunches, reverse crunches involve minimal movement in the upper body, which can reduce strain on the neck and lower back.
Variations:
Straight Leg Reverse Crunch: Perform the reverse crunch with straight legs to increase the intensity and engage the hip flexors more.
Weighted Reverse Crunch: Hold a dumbbell or medicine ball between your feet to add resistance and make the exercise more challenging.
Elevated Reverse Crunch: Perform the exercise with your feet elevated on a bench or exercise ball to increase the difficulty and target the core even more.
The reverse crunch is a great exercise to incorporate into your core workout routine, especially if you’re looking to target the lower abdominals. As with any exercise, proper form is crucial to avoid injury and maximize effectiveness.
""",
            "Abdominal",
            R.raw.reversecrunch,
            null,
            false,
        ),
        Routine(
            27,
            "Scissors",
            """Scissors (or Scissor Kicks) is a dynamic abdominal exercise that targets the lower abs and hip flexors. It also engages the core, especially the obliques and lower back muscles, as it requires coordination and stability. This exercise involves alternating leg movements in a scissor-like fashion, which not only strengthens the core but also helps improve flexibility and endurance.
Steps to Do Scissors
1.	Start in a Lying Position: Lie flat on your back on an exercise mat, with your legs extended straight out in front of you. Place your hands under your glutes or by your sides for support. Keep your head resting on the mat, and make sure your lower back is pressed into the floor.
2.	Lift Your Legs: Raise both legs off the ground about 6-8 inches, keeping them straight and together. Engage your core to stabilize your body.
3.	Perform the Scissor Motion: Slowly lower your right leg towards the floor while keeping your left leg elevated. Once your right leg is a few inches above the floor, bring your right leg back up and lower your left leg towards the floor in a controlled motion.
4.	Cross and Alternate: Continue alternating the movement, crossing one leg over the other like the motion of scissors. Make sure to keep your legs straight and maintain control over the movement.
5.	Focus on Breathing: Keep your core tight and breathe steadily throughout the movement. Avoid holding your breath.
6.	Repeat: Perform the desired number of repetitions, maintaining slow, controlled movements. Aim for 10-15 repetitions per side, or longer for increased intensity.
Tips for Good Form
Don’t let your lower back arch: Keep your lower back pressed into the floor to prevent strain. If you feel your lower back lifting off the ground, raise your legs higher to reduce strain.
Keep your core engaged: Engage your abdominal muscles throughout the movement, and avoid using momentum. The slower and more controlled the motion, the more effective the exercise.
Leg height: Adjust the height of your legs based on your fitness level. The higher your legs, the easier the exercise, while lowering them closer to the floor increases the challenge.
Avoid jerky movements: Perform the scissors motion with smooth and controlled movements. Jerky or fast movements can increase the risk of injury.
Benefits of Scissors
Targets Lower Abs: Scissor kicks specifically work the lower part of the abdominal muscles, which can be harder to target with other traditional exercises.
Improves Coordination and Stability: Since the exercise requires alternating leg movements, it helps improve overall body coordination and core stability.
Increases Endurance: Performing scissors for a longer period helps build muscular endurance in the abs and hip flexors.
Engages Obliques and Hip Flexors: This exercise also engages the obliques, helping to strengthen the sides of your core, as well as the hip flexors.
Variations:
Scissors with a Hold: At the midpoint of each leg movement, hold the leg in position for a few seconds to increase the challenge and work the core even more.
Weighted Scissors: Hold a weight or medicine ball between your feet to add resistance and increase the intensity of the exercise.
Scissors with Leg Extension: Add a leg extension at the end of the scissors movement for a greater challenge and to further engage the lower abs.
Scissor kicks are an excellent addition to any ab workout routine, as they target the lower abs, challenge your balance, and help improve flexibility. Keep your movements controlled and focus on proper form to get the most out of the exercise.
""",
            "Abdominal",
            R.raw.scissors,
            null,
            false,
        ),
        Routine(
            28,
            "Bicycle",
            """The bicycle crunch is an effective core exercise that targets the rectus abdominis (the “six-pack” muscles), obliques (side abs), and hip flexors. It’s a dynamic, twisting movement that mimics a bicycle pedaling motion, making it an excellent exercise for engaging multiple abdominal muscles at once.
Steps to Do a Bicycle Crunch
1.	Start in a Lying Position: Lie flat on your back on a mat, with your knees bent and feet flat on the floor. Place your hands lightly behind your head, with your elbows pointed out to the sides. Be careful not to pull on your neck.
2.	Lift Your Head and Shoulders: Engage your core and raise your head, neck, and shoulders off the floor while keeping your lower back pressed into the mat. This engages the abdominal muscles.
3.	Begin the Pedaling Motion:
Bring your right knee towards your chest while simultaneously twisting your torso to bring your left elbow towards your right knee.
Extend your left leg out straight, making sure not to let it touch the floor.
At the same time, rotate your upper body to bring your left elbow towards your right knee, mimicking a cycling motion.
4.	Switch Sides:
As you extend your right leg and bring your left knee toward your chest, twist your torso to the left, bringing your right elbow toward your left knee.
Keep alternating sides in a smooth, controlled pedaling motion.
5.	Continue Alternating: Repeat this movement, making sure to engage your core and keep your movements slow and controlled. Focus on the twisting motion rather than pulling with your arms.
6.	Finish: Perform the desired number of repetitions (typically 12-15 per side, or 30-60 seconds for continuous cycling).
Tips for Good Form
Don’t pull on your neck: Keep your hands lightly behind your head to support your neck, but don’t pull on it. The movement should come from your torso.
Keep your lower back pressed into the mat: To avoid strain on the lower back, ensure that your lower back is always in contact with the floor.
Control your movement: Perform the movement slowly and with control. Fast, jerky motions can lead to poor form and reduce the effectiveness of the exercise.
Engage your core: Focus on using your abs to twist your torso, rather than relying on momentum. The slower and more deliberate the movement, the better the activation of the core.
Benefits of Bicycle Crunches
Targets Multiple Core Muscles: Bicycle crunches engage the upper abs, lower abs, and obliques, providing a full core workout.
Improves Oblique Definition: The twisting motion of the exercise is especially effective for sculpting the obliques and giving you a more toned waistline.
Increases Core Strength and Endurance: By continuously alternating sides and engaging the core, the bicycle crunch helps build both strength and endurance in your abdominal muscles.
Promotes Functional Movement: The twisting motion helps enhance rotational strength, which can improve daily activities like lifting, twisting, and bending.
Variations:
Weighted Bicycle Crunch: Hold a weight (such as a medicine ball or dumbbell) in your hands or between your feet to increase the resistance and make the exercise more challenging.
Slow Bicycle Crunch: Slow down the movement to focus more on the contraction in the abs and obliques, increasing time under tension and enhancing muscle activation.
Reverse Bicycle Crunch: While maintaining the same motion, perform the bicycle crunch by lowering the legs down and focusing on the contraction of the lower abs.
The bicycle crunch Is an effective and efficient exercise for targeting your entire core. Focus on form and control, and avoid rushing through the movement for the best results.
""",
            "Abdominal",
            R.raw.bicycle,
            null,
            false,
        ),

        //

        Routine(
            30,
            "Jogging",
            """Sample Description""",
            "Activity",
            null,
            R.drawable.jogging,
            false,
        ),
        Routine(
            31,
            "Warm Up",
            """Sample Description""",
            "Activity",
            R.raw.warmup,
            null,
            false,
        ),
        Routine(
            32,
            "Warp Up",
            """Sample Description""",
            "Activity",
            R.raw.warmup,
            null,
            false,
        ),


        )
    val workouts = listOf(
        Workouts(
            1, 1, "4 x 50m sprints at moderate effort (interval: 1.5 minutes rest between sprints)",
            "To introduce your body to sprinting, focusing on technique and rhythm",
            "Focus on getting a smooth start, maintaining a steady speed throughout the sprint, and keeping a good posture. Rest fully between sprints for recovery.",
            "100m",
            "100m"
        ),

        Workouts(
            2, 2, "5 x 50m sprints, focusing on relaxed speed (interval: 1-minute rest)",
            "Improve your sprinting form while developing speed without overexerting",
            "Run with a controlled pace, focusing on smooth and fluid movements rather than speed. Rest fully between intervals.",
            "100m",
            "100m"
        ),

        Workouts(
            3, 3, "3 x 40m start drills, focusing on reaction time (interval: 1-minute rest)",
            "Improve your reaction time at the start of a sprint",
            "Start from a standing position and sprint as fast as possible to cover the 40m distance. Focus on getting off the line quickly and explosively.",
            "100m",
            "100m"
        ),

        Workouts(
            4, 4, "4 x 60m sprints at moderate speed (interval: 1.5 minutes rest)",
            "Develop sprinting endurance and ability to sustain moderate speed",
            "Focus on maintaining consistent speed and good running posture. Take full rest in between to recover for the next sprint.",
            "100m",
            "100m"
        ),

        Workouts(
            5, 5, "1 x 100m time trial at maximum effort, record time for assessment",
            "Measure your progress and see how your maximum sprinting capacity has improved",
            "Warm up thoroughly, then give your best effort over the full 100m. Record your time and compare it with future trials.",
            "100m",
            "100m"
        ),

        Workouts(
            6, 6, "3 x 30m sprints, focusing on form and relaxation (interval: 1-minute rest)",
            "Work on sprinting technique and relaxation during high-speed running",
            "Keep your body relaxed, especially your shoulders and face. Focus on smooth strides and controlled breathing.",
            "100m",
            "100m"
        ),

        Workouts(
            7, 7, "Rest and recovery day",
            "Allow the body to recover from the intense sprints",
            "Take it easy today. Light walking or stretching can be beneficial.",
            null,
            null
        ),

        Workouts(
            8, 8, "4 x 50m sprints (interval: 1-minute rest)",
            "Build on previous training and maintain sprinting stamina",
            "Aim for consistent performance at a moderate effort. Focus on posture and stride mechanics during each sprint.",
            "100m",
            "100m"
        ),

        Workouts(
            9, 9, "3 x 40m start drills with explosive focus (interval: 1-minute rest)",
            "Improve your explosive start from a static position",
            "Push off as hard as you can and work on driving forward with maximal intensity from the start.",
            "100m",
            "100m"
        ),

        Workouts(
            10, 10, "5 x 60m sprints at moderate effort (interval: 1.5 minutes rest)",
            "Focus on sustaining a moderate pace for longer distances",
            "Keep your posture upright and maintain consistent speed across each 60m. Keep your legs moving at a strong, consistent pace throughout.",
            "100m",
            "100m"
        ),
        Workouts(
            11,
            11,
            "5 x 70m sprints at 75% max speed (interval: 2-minute rest).",
            "Build speed endurance while focusing on controlled pacing.",
            "Build speed endurance while focusing on controlled pacing.",
            "Aim for a consistent 75% effort across all sprints. Focus on staying relaxed and maintaining form throughout the distance.",
            "100m"
        ),
        Workouts(
            12,
            12,
            "4 x 60m sprints, focusing on maintaining speed (interval: 1.5-minute rest).",
            "Focus on maintaining a high speed for longer distances.",
            "Focus on maintaining a high speed for longer distances.",
            "Start fast and work on holding your speed across the 60m distance. Ensure you’re not slowing down too much in the second half of each sprint.",
            "100m"
        ),
        Workouts(
            13,
            13,
            "3 x 40m acceleration starts (interval: 1.5-minute rest).",
            "Improve acceleration and explosive speed from a standing start.",
            "Improve acceleration and explosive speed from a standing start.",
            "Focus on driving your knees high and pumping your arms hard during the first 20m. Build to your maximum speed by the 40m mark.",
            "100m"
        ),
        Workouts(
            14,
            14,
            "6 x 50m sprints, focusing on form and relaxation (interval: 1-minute rest).",
            "Focus on form, relaxation, and technique over speed.",
            "Focus on form, relaxation, and technique over speed.",
            "Relax your upper body, maintain a straight posture, and keep your arms moving fluidly to improve efficiency.",
            "100m"
        ),
        Workouts(
            15,
            15,
            "1 x 100m time trial at maximum effort, record time and compare to previous week.",
            "Test your progress and compare your results to prior performance.",
            "Test your progress and compare your results to prior performance.",
            "Warm up thoroughly and give your best effort during the 100m. Record your time and analyze if there’s improvement since your last time trial.",
            "100m"
        ),
        Workouts(
            16,
            16,
            "5 x 60m sprints, focusing on speed control (interval: 1.5 minutes rest).",
            "Work on controlling your speed and pacing during sprints.",
            "Work on controlling your speed and pacing during sprints.",
            "Start at full speed, but focus on controlling your stride length and frequency to maintain speed without burning out.",
            "100m"
        ),
        Workouts(
            17,
            17,
            "Light stretching or yoga.",
            "Allow your muscles to recover while improving flexibility.",
            "Allow your muscles to recover while improving flexibility.",
            "Focus on gentle stretches to improve mobility and relax your muscles.",
            "100m"
        ),
        Workouts(
            18,
            18,
            "4 x 70m sprints at 80% max speed (interval: 2-minute rest).",
            "Continue developing speed endurance, now pushing closer to 80% effort.",
            "Continue developing speed endurance, now pushing closer to 80% effort.",
            "Focus on maintaining form, but work on pushing harder while controlling your breathing and stride mechanics.",
            "100m"
        ),
        Workouts(
            19,
            19,
            "3 x 30m start drills with a focus on reaction time (interval: 1-minute rest).",
            "Sharpen your reaction time to start sprints faster.",
            "Sharpen your reaction time to start sprints faster.",
            "Focus on explosive reaction when you hear the signal, and push as hard as you can from the start line.",
            "100m"
        ),
        Workouts(
            20,
            20,
            "6 x 60m sprints at 80% effort (interval: 2 minutes rest).",
            "Develop speed endurance at a higher intensity.",
            "Develop speed endurance at a higher intensity.",
            "Maintain your form and push through the entire 60m at 80% effort, focusing on consistency across all sets.",
            "100m"
        ),
        // Expert Level (Days 21-30)
        Workouts(
            21,
            21,
            "6 x 80m sprints at 85% max speed (interval: 2.5-minute rest).",
            "Push yourself to higher intensities while maintaining form over longer distances.",
            "Push yourself to higher intensities while maintaining form over longer distances.",
            "Focus on explosive power during the first 30m, then work on sustaining the effort across the full 80m.",
            "100m"
        ),
        Workouts(
            22,
            22,
            "5 x 60m sprints with sustained speed focus (interval: 1.5-minute rest).",
            "Focus on sustaining maximum speed for longer durations.",
            "Focus on sustaining maximum speed for longer durations.",
            "Sprint at a controlled but fast pace, focusing on efficiency and technique throughout the 60m.",
            "100m"
        ),
        Workouts(
            23,
            23,
            "4 x 50m acceleration starts, focusing on powerful drive (interval: 1-minute rest).",
            "Improve your drive phase and initial burst of speed.",
            "Improve your drive phase and initial burst of speed.",
            "Focus on strong push-offs from the start and powering through the first 20 meters with maximum force.",
            "100m"
        ),
        Workouts(
            24,
            24,
            "6 x 70m sprints with form and relaxation emphasis (interval: 2-minute rest).",
            "Improve sprinting efficiency and maintain relaxed form under speed.",
            "Improve sprinting efficiency and maintain relaxed form under speed.",
            "Relax your arms, shoulders, and face during each sprint, while maintaining strong strides and proper technique.",
            "100m"
        ),
        Workouts(
            25,
            25,
            "1 x 100m time trial at maximum effort, assess improvement.",
            "Assess your sprinting performance after 4 weeks of training.",
            "Assess your sprinting performance after 4 weeks of training.",
            "Ensure you're properly warmed up and go all out for the 100m. Record your time for comparison with previous trials.",
            "100m"
        ),
        Workouts(
            26,
            26,
            "4 x 80m sprints at 85% intensity (interval: 2-minute rest).",
            "Improve your sprint endurance and capacity to maintain high speeds.",
            "Improve your sprint endurance and capacity to maintain high speeds.",
            "Focus on a strong and efficient sprint form throughout the entire 80m distance. Keep your posture strong and your strides powerful. Relax your upper body to prevent fatigue.",
            "100m"
        ),

        Workouts(
            27,
            27,
            "Light stretching or yoga.",
            "Allow for muscle recovery while improving flexibility and mobility.",
            "Allow for muscle recovery while improving flexibility and mobility.",
            "Focus on relaxing stretches, particularly on your legs, hips, and lower back. Aim for a calm pace and deep breathing during your stretches.",
            "100m"
        ),

        Workouts(
            28,
            28,
            "5 x 70m sprints focusing on speed endurance (interval: 2-minute rest).",
            "Work on your ability to sustain speed over longer distances.",
            "Work on your ability to sustain speed over longer distances.",
            "Focus on pacing yourself throughout the sprint, pushing hard at the start, and maintaining speed for the entire 70m. Keep your form tight and controlled.",
            "100m"
        ),

        Workouts(
            29,
            29,
            "3 x 40m all-out starts, focusing on explosive drive (interval: 1-minute rest).",
            "Enhance your ability to explode off the start line at full intensity.",
            "Enhance your ability to explode off the start line at full intensity.",
            "Focus on driving your knees up and pumping your arms to generate power quickly from the start. Push yourself to give maximum effort during each acceleration phase.",
            "100m"
        ),

        Workouts(
            30,
            30,
            "6 x 80m sprints at 90% effort (interval: 2.5-minute rest).",
            "Test your speed and endurance at high intensity.",
            "Test your speed and endurance at high intensity.",
            "Push yourself to 90% effort during each sprint. Focus on maintaining your sprint form and pushing as hard as you can all the way to the finish line.",
            "100m"
        ),
        Workouts(
            id = 31,
            day = 1,
            mainWorkOut = "4 x 200m at 60% effort (2 minutes rest).",
            purpose = "Build endurance and pacing skills.",
            tips = "Maintain a controlled, steady pace while focusing on breathing and stride mechanics.",
            scope = "800m"
        ),
        Workouts(
            id = 32,
            day = 2,
            mainWorkOut = "3 x 300m at 60% effort (3 minutes rest).",
            purpose = "Introduce longer intervals to develop stamina.",
            tips = "Start slow and pick up slightly in the last 50m.",
            scope = "800m"
        ),
        Workouts(
            id = 33,
            day = 3,
            mainWorkOut = "5 x 150m at 70% effort (2 minutes rest).",
            purpose = "Improve mid-distance speed and control.",
            tips = "Focus on a consistent pace with smooth transitions.",
            scope = "800m"
        ),
        Workouts(
            id = 34,
            day = 4,
            mainWorkOut = "4 x 250m at 60% effort (2 minutes rest).",
            purpose = "Strengthen endurance while maintaining moderate intensity.",
            tips = "Run comfortably and focus on holding your form.",
            scope = "800m"
        ),
        Workouts(
            id = 35,
            day = 5,
            mainWorkOut = "1 x 800m time trial at 90% effort, record your time.",
            purpose = "Test your baseline and assess improvements.",
            tips = "Divide the run into two 400m sections, maintaining a slightly faster pace in the second half.",
            scope = "800m"
        ),
        Workouts(
            id = 36,
            day = 6,
            mainWorkOut = "4 x 200m at 70% effort (2 minutes rest).",
            purpose = "Enhance speed control and pacing over shorter intervals.",
            tips = "Focus on even splits and maintaining energy throughout.",
            scope = "800m"
        ),
        Workouts(
            id = 37,
            day = 7,
            mainWorkOut = "Light walking or yoga.",
            purpose = "Allow muscles to recover.",
            tips = "Rest and relax, focusing on light stretching or yoga.",
            scope = "800m"
        ),
        Workouts(
            id = 38,
            day = 8,
            mainWorkOut = "3 x 400m at 65% effort (4 minutes rest).",
            purpose = "Build race-specific endurance for middle-distance events.",
            tips = "Maintain a steady pace, focusing on your breathing and form.",
            scope = "800m"
        ),
        Workouts(
            id = 39,
            day = 9,
            mainWorkOut = "5 x 150m at 75% effort (2.5 minutes rest).",
            purpose = "Work on speed for finishing kicks.",
            tips = "Push yourself during the last 50m of each sprint.",
            scope = "800m"
        ),
        Workouts(
            id = 40,
            day = 10,
            mainWorkOut = "6 x 200m at 70% effort (3 minutes rest).",
            purpose = "Reinforce pacing control and sustained speed.",
            tips = "Maintain consistent effort throughout each sprint.",
            scope = "800m"
        ),
        Workouts(
            id = 41,
            day = 11,
            mainWorkOut = "5 x 400m at 65% effort (3 minutes rest).",
            purpose = "Develop pacing and endurance over longer intervals.",
            tips = "Maintain consistent splits and control your breathing throughout.",
            scope = "800m"
        ),
        Workouts(
            id = 42,
            day = 12,
            mainWorkOut = "4 x 300m at 70% effort (2.5 minutes rest).",
            purpose = "Build stamina with faster-paced intervals.",
            tips = "Focus on maintaining speed and form through each interval.",
            scope = "800m"
        ),
        Workouts(
            id = 43,
            day = 13,
            mainWorkOut = "3 x 500m at 60% effort (4 minutes rest).",
            purpose = "Push your endurance threshold while staying relaxed.",
            tips = "Stay relaxed and keep your form consistent throughout the run.",
            scope = "800m"
        ),
        Workouts(
            id = 44,
            day = 14,
            mainWorkOut = "6 x 200m at 75% effort (2 minutes rest).",
            purpose = "Enhance speed for finishing sprints.",
            tips = "Focus on a strong finish and form during each sprint.",
            scope = "800m"
        ),
        Workouts(
            id = 45,
            day = 15,
            mainWorkOut = "1 x 800m time trial at maximum effort.",
            purpose = "Test your progress and evaluate pacing strategies.",
            tips = "Try to push through the first 400m and finish strong in the second half.",
            scope = "800m"
        ),
        Workouts(
            id = 46,
            day = 16,
            mainWorkOut = "4 x 600m at 65% effort (4 minutes rest).",
            purpose = "Build sustained endurance and pacing discipline.",
            scope = "800m"
        ),
        Workouts(
            id = 47,
            day = 17,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Allow muscles to recover and stretch.",
            scope = "800m"
        ),
        Workouts(
            id = 48,
            day = 18,
            mainWorkOut = "5 x 300m at 70% effort (3 minutes rest).",
            purpose = "Develop rhythm and speed endurance.",
            scope = "800m"
        ),
        Workouts(
            id = 49,
            day = 19,
            mainWorkOut = "3 x 400m with negative splits (start slow, finish fast; 3 minutes rest).",
            purpose = "Train for strong finishes.",
            scope = "800m"
        ),
        Workouts(
            id = 50,
            day = 20,
            mainWorkOut = "6 x 200m at 80% effort (2 minutes rest).",
            purpose = "Focus on race-pace speed and efficiency.",
            scope = "800m"
        ),

        // Expert Level (Days 21-30)
        Workouts(
            id = 51,
            day = 21,
            mainWorkOut = "5 x 500m at 70% effort (3.5 minutes rest).",
            purpose = "Build endurance at higher intensities.",
            scope = "800m"
        ),
        Workouts(
            id = 52,
            day = 22,
            mainWorkOut = "4 x 600m at 75% effort (4 minutes rest).",
            purpose = "Focus on sustained speed endurance.",
            scope = "800m"
        ),
        Workouts(
            id = 53,
            day = 23,
            mainWorkOut = "3 x 400m with negative splits (3 minutes rest).",
            purpose = "Sharpen finishing strength.",
            scope = "800m"
        ),
        Workouts(
            id = 54,
            day = 24,
            mainWorkOut = "6 x 200m at 85% effort (2.5 minutes rest).",
            purpose = "Improve sprinting speed and anaerobic capacity.",
            scope = "800m"
        ),
        Workouts(
            id = 55,
            day = 25,
            mainWorkOut = "1 x 800m time trial at 100% effort.",
            purpose = "Measure overall progress and pacing ability.",
            scope = "800m"
        ),
        Workouts(
            id = 56,
            day = 26,
            mainWorkOut = "5 x 300m at 80% effort (3 minutes rest).",
            purpose = "Push speed endurance to near race pace.",
            scope = "800m"
        ),
        Workouts(
            id = 57,
            day = 27,
            mainWorkOut = "Light walking, yoga, or stretching.",
            purpose = "Active recovery day.",
            scope = "800m"
        ),
        Workouts(
            id = 58,
            day = 28,
            mainWorkOut = "4 x 600m at 80% effort (4 minutes rest).",
            purpose = "Finalize endurance training with higher intensity.",
            scope = "800m"
        ),
        Workouts(
            id = 59,
            day = 29,
            mainWorkOut = "3 x 400m at 85% effort (3 minutes rest).",
            purpose = "Sharpen race-day speed and finishing technique.",
            scope = "800m"
        ),
        Workouts(
            id = 60,
            day = 30,
            mainWorkOut = "6 x 300m at 90% effort (3 minutes rest).",
            purpose = "Simulate race conditions and maximize anaerobic capacity.",
            scope = "800m"
        ),
        Workouts(
            id = 61,
            day = 1,
            mainWorkOut = "4 x 400m at an easy pace, 1.5 minutes rest between intervals.",
            purpose = "Build aerobic endurance and adapt to consistent running.",
            scope = "300m"
        ),
        Workouts(
            id = 62,
            day = 2,
            mainWorkOut = "5 x 300m at a comfortable pace, 1-minute rest.",
            purpose = "Increase speed slightly while maintaining good form.",
            scope = "300m"
        ),
        Workouts(
            id = 63,
            day = 3,
            mainWorkOut = "3 x 600m, focus on steady pace, 2-minute rest.",
            purpose = "Improve pacing over longer intervals.",
            scope = "300m"
        ),
        Workouts(
            id = 64,
            day = 4,
            mainWorkOut = "4 x 500m, maintain a relaxed speed, 1.5 minutes rest.",
            purpose = "Increase distance and maintain a relaxed effort.",
            scope = "300m"
        ),
        Workouts(
            id = 65,
            day = 5,
            mainWorkOut = "1 x 1000m at a challenging pace, assess and record time.",
            purpose = "Test your current fitness level and track progress.",
            scope = "300m"
        ),
        Workouts(
            id = 66,
            day = 6,
            mainWorkOut = "3 x 500m at moderate intensity, 1-minute rest.",
            purpose = "Improve your ability to sustain moderate intensity.",
            scope = "300m"
        ),
        Workouts(
            id = 67,
            day = 7,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Aid recovery and maintain flexibility.",
            scope = "300m"
        ),
        Workouts(
            id = 68,
            day = 8,
            mainWorkOut = "4 x 400m at a comfortable pace, 1-minute rest.",
            purpose = "Focus on maintaining a steady pace with shorter rest.",
            scope = "300m"
        ),
        Workouts(
            id = 69,
            day = 9,
            mainWorkOut = "3 x 600m with steady pace, 1.5-minute rest.",
            purpose = "Build endurance with a steady, moderate effort.",
            scope = "300m"
        ),
        Workouts(
            id = 70,
            day = 10,
            mainWorkOut = "5 x 500m, moderate intensity, 1-minute rest.",
            purpose = "Improve stamina and speed over longer intervals.",
            scope = "300m"
        ),

        // Intermediate Level (Days 11-20)
        Workouts(
            id = 71,
            day = 11,
            mainWorkOut = "5 x 600m at 70% effort, 2 minutes rest.",
            purpose = "Increase endurance at a moderate effort level.",
            scope = "300m"
        ),
        Workouts(
            id = 72,
            day = 12,
            mainWorkOut = "4 x 700m with a steady pace, 1.5-minute rest.",
            purpose = "Build longer-distance endurance at a sustainable pace.",
            scope = "300m"
        ),
        Workouts(
            id = 73,
            day = 13,
            mainWorkOut = "3 x 800m, maintain a moderate effort, 2-minute rest.",
            purpose = "Develop pacing and endurance over longer intervals.",
            scope = "300m"
        ),
        Workouts(
            id = 74,
            day = 14,
            mainWorkOut = "6 x 500m with a focus on even pacing, 1-minute rest.",
            purpose = "Improve consistency in pacing over multiple intervals.",
            scope = "300m"
        ),
        Workouts(
            id = 75,
            day = 15,
            mainWorkOut = "1 x 1500m at a challenging pace, record time and compare to previous week.",
            purpose = "Measure progress and increase running distance at a hard pace.",
            scope = "300m"
        ),
        Workouts(
            id = 76,
            day = 16,
            mainWorkOut = "5 x 600m at 75% effort, 1.5 minutes rest.",
            purpose = "Increase stamina and intensity at a moderate pace.",
            scope = "300m"
        ),
        Workouts(
            id = 77,
            day = 17,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Recover and improve flexibility.",
            scope = "300m"
        ),
        Workouts(
            id = 78,
            day = 18,
            mainWorkOut = "4 x 800m at 80% effort, 2 minutes rest.",
            purpose = "Build endurance while pushing your effort slightly harder.",
            scope = "300m"
        ),
        Workouts(
            id = 79,
            day = 19,
            mainWorkOut = "3 x 1000m, focus on pacing, 2.5-minute rest.",
            purpose = "Improve pacing over long intervals.",
            scope = "300m"
        ),
        Workouts(
            id = 80,
            day = 20,
            mainWorkOut = "6 x 600m at 80% effort, 1.5-minute rest.",
            purpose = "Improve both speed and endurance at a moderate-high intensity.",
            scope = "300m"
        ),
        Workouts(
            id = 81,
            day = 21,
            mainWorkOut = "6 x 700m at 85% effort, 2.5-minute rest.",
            purpose = "Build speed endurance and increase overall intensity.",
            scope = "300m"
        ),
        Workouts(
            id = 82,
            day = 22,
            mainWorkOut = "5 x 800m at a controlled pace, 1.5-minute rest.",
            purpose = "Maintain pace and consistency over a longer distance.",
            scope = "300m"
        ),
        Workouts(
            id = 83,
            day = 23,
            mainWorkOut = "4 x 1000m with steady pacing, 2.5-minute rest.",
            purpose = "Focus on endurance and sustaining a high level of effort over long distances.",
            scope = "300m"
        ),
        Workouts(
            id = 84,
            day = 24,
            mainWorkOut = "6 x 700m focusing on endurance, 2-minute rest.",
            purpose = "Increase endurance at high effort levels.",
            scope = "300m"
        ),
        Workouts(
            id = 85,
            day = 25,
            mainWorkOut = "1 x 2000m at maximum effort, record time for comparison.",
            purpose = "Test your long-distance performance and measure progress.",
            scope = "300m"
        ),
        Workouts(
            id = 86,
            day = 26,
            mainWorkOut = "5 x 800m at 85% effort, 2-minute rest.",
            purpose = "Push endurance with more intense efforts.",
            scope = "300m"
        ),
        Workouts(
            id = 87,
            day = 27,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Relax muscles and focus on recovery.",
            scope = "300m"
        ),
        Workouts(
            id = 88,
            day = 28,
            mainWorkOut = "6 x 700m focusing on endurance, 2-minute rest.",
            purpose = "Reinforce endurance at a high intensity.",
            scope = "300m"
        ),
        Workouts(
            id = 89,
            day = 29,
            mainWorkOut = "3 x 1200m with steady pace, 3-minute rest.",
            purpose = "Improve pacing over longer distances.",
            scope = "300m"
        ),
        Workouts(
            id = 90,
            day = 30,
            mainWorkOut = "5 x 800m at 90% effort, 2.5-minute rest.",
            purpose = "Maximize speed and endurance.",
            scope = "300m"
        ),
        Workouts(
            id = 91,
            day = 1,
            mainWorkOut = "4 x 600m at an easy pace, 1.5 minutes rest between intervals.",
            purpose = "Build endurance with a comfortable pace and focus on form.",
            scope = "5000m"
        ),
        Workouts(
            id = 92,
            day = 2,
            mainWorkOut = "5 x 500m at a comfortable pace, 1-minute rest.",
            purpose = "Improve stamina and start adjusting to slightly faster intervals.",
            scope = "5000m"
        ),
        Workouts(
            id = 93,
            day = 3,
            mainWorkOut = "3 x 800m, focus on steady pace, 2-minute rest.",
            purpose = "Build your ability to maintain a steady pace over longer distances.",
            scope = "5000m"
        ),
        Workouts(
            id = 94,
            day = 4,
            mainWorkOut = "4 x 700m, maintain a relaxed speed, 1.5 minutes rest.",
            purpose = "Work on pacing and maintaining a comfortable but controlled effort.",
            scope = "5000m"
        ),
        Workouts(
            id = 95,
            day = 5,
            mainWorkOut = "1 x 1500m at a moderate effort, assess and record time.",
            purpose = "Test your fitness and pacing over a moderate distance.",
            scope = "5000m"
        ),
        Workouts(
            id = 96,
            day = 6,
            mainWorkOut = "3 x 700m at moderate intensity, 1-minute rest.",
            purpose = "Improve speed and endurance with moderate effort.",
            scope = "5000m"
        ),
        Workouts(
            id = 97,
            day = 7,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Recovery and flexibility.",
            scope = "5000m"
        ),
        Workouts(
            id = 98,
            day = 8,
            mainWorkOut = "4 x 600m at a comfortable pace, 1-minute rest.",
            purpose = "Focus on consistency and maintaining a steady pace.",
            scope = "5000m"
        ),
        Workouts(
            id = 99,
            day = 9,
            mainWorkOut = "3 x 800m with steady pace, 1.5-minute rest.",
            purpose = "Build endurance and control pacing over a longer distance.",
            scope = "5000m"
        ),
        Workouts(
            id = 100,
            day = 10,
            mainWorkOut = "5 x 700m at moderate intensity, 1-minute rest.",
            purpose = "Further develop stamina at a moderate pace.",
            scope = "5000m"
        ),
        Workouts(
            id = 101,
            day = 11,
            mainWorkOut = "5 x 800m at 70% effort, 2 minutes rest.",
            purpose = "Build endurance while working at a moderate intensity.",
            scope = "5000m"
        ),
        Workouts(
            id = 102,
            day = 12,
            mainWorkOut = "4 x 1000m with a steady pace, 2-minute rest.",
            purpose = "Increase stamina and improve pacing at a steady pace.",
            scope = "5000m"
        ),
        Workouts(
            id = 103,
            day = 13,
            mainWorkOut = "3 x 1200m, maintain a moderate effort, 2.5-minute rest.",
            purpose = "Push endurance at a moderate pace over a longer distance.",
            scope = "5000m"
        ),
        Workouts(
            id = 104,
            day = 14,
            mainWorkOut = "6 x 700m with a focus on even pacing, 1-minute rest.",
            purpose = "Improve your ability to pace consistently over multiple intervals.",
            scope = "5000m"
        ),
        Workouts(
            id = 105,
            day = 15,
            mainWorkOut = "1 x 2000m at a challenging pace, record time and compare to previous week.",
            purpose = "Test your fitness at a longer distance and gauge progress.",
            scope = "5000m"
        ),
        Workouts(
            id = 106,
            day = 16,
            mainWorkOut = "5 x 800m at 75% effort, 2 minutes rest.",
            purpose = "Increase effort slightly, focusing on pacing at moderate intensity.",
            scope = "5000m"
        ),
        Workouts(
            id = 107,
            day = 17,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Recovery and improve flexibility.",
            scope = "5000m"
        ),
        Workouts(
            id = 108,
            day = 18,
            mainWorkOut = "4 x 1200m at 80% effort, 2.5 minutes rest.",
            purpose = "Work on endurance at a higher effort level.",
            scope = "5000m"
        ),
        Workouts(
            id = 109,
            day = 19,
            mainWorkOut = "3 x 1500m, focus on pacing, 3-minute rest.",
            purpose = "Build endurance and develop better pacing over longer intervals.",
            scope = "5000m"
        ),
        Workouts(
            id = 110,
            day = 20,
            mainWorkOut = "6 x 800m at 80% effort, 2 minutes rest.",
            purpose = "Maintain a challenging effort level and build endurance.",
            scope = "5000m"
        ),
        Workouts(
            id = 111,
            day = 21,
            mainWorkOut = "6 x 1000m at 85% effort, 2.5-minute rest.",
            purpose = "Build endurance and speed at a high effort level.",
            scope = "5000m"
        ),
        Workouts(
            id = 112,
            day = 22,
            mainWorkOut = "5 x 1200m at a controlled pace, 2-minute rest.",
            purpose = "Improve pacing over longer distances while maintaining control.",
            scope = "5000m"
        ),
        Workouts(
            id = 113,
            day = 23,
            mainWorkOut = "4 x 1500m with steady pacing, 3-minute rest.",
            purpose = "Increase stamina and pacing over very long intervals.",
            scope = "5000m"
        ),
        Workouts(
            id = 114,
            day = 24,
            mainWorkOut = "6 x 1000m focusing on endurance, 2-minute rest.",
            purpose = "Build endurance at a high intensity.",
            scope = "5000m"
        ),
        Workouts(
            id = 115,
            day = 25,
            mainWorkOut = "1 x 3000m at maximum effort, record time for comparison.",
            purpose = "Test your long-distance endurance and track your progress.",
            scope = "5000m"
        ),
        Workouts(
            id = 116,
            day = 26,
            mainWorkOut = "5 x 1200m at 85% effort, 2-minute rest.",
            purpose = "Push endurance and increase intensity at longer distances.",
            scope = "5000m"
        ),
        Workouts(
            id = 117,
            day = 27,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Recovery and flexibility.",
            scope = "5000m"
        ),
        Workouts(
            id = 118,
            day = 28,
            mainWorkOut = "6 x 1000m focusing on endurance, 2-minute rest.",
            purpose = "Work on stamina and endurance at a high intensity.",
            scope = "5000m"
        ),
        Workouts(
            id = 119,
            day = 29,
            mainWorkOut = "3 x 2000m with steady pace, 3.5-minute rest.",
            purpose = "Work on pacing and endurance for longer distances.",
            scope = "5000m"
        ),
        Workouts(
            id = 120,
            day = 30,
            mainWorkOut = "5 x 1200m at 90% effort, 2.5-minute rest.",
            purpose = "Maximize endurance and speed at a high intensity.",
            scope = "5000m"
        ),
        Workouts(
            id = 121,
            day = 1,
            mainWorkOut = "4 x 200m at an easy pace, 2 minutes rest between intervals.",
            purpose = "Build a foundation for speed and form.",
            scope = "200m"
        ),
        Workouts(
            id = 122,
            day = 2,
            mainWorkOut = "5 x 150m sprints at a comfortable pace, 90-second rest.",
            purpose = "Develop speed with a focus on short sprints.",
            scope = "200m"
        ),
        Workouts(
            id = 123,
            day = 3,
            mainWorkOut = "3 x 200m, focus on steady pacing, 2-minute rest.",
            purpose = "Improve your ability to maintain a consistent pace.",
            scope = "200m"
        ),
        Workouts(
            id = 124,
            day = 4,
            mainWorkOut = "4 x 200m, maintain a relaxed speed, 2 minutes rest.",
            purpose = "Improve pacing while keeping the effort relaxed.",
            scope = "200m"
        ),
        Workouts(
            id = 125,
            day = 5,
            mainWorkOut = "1 x 200m at a moderate effort, assess and record time.",
            purpose = "Track your progress and assess your sprinting speed.",
            scope = "200m"
        ),
        Workouts(
            id = 126,
            day = 6,
            mainWorkOut = "3 x 200m at moderate intensity, 1.5-minute rest.",
            purpose = "Increase intensity while maintaining good form.",
            scope = "200m"
        ),
        Workouts(
            id = 127,
            day = 7,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Recovery and flexibility.",
            scope = "200m"
        ),
        Workouts(
            id = 128,
            day = 8,
            mainWorkOut = "4 x 150m sprints, 1.5 minutes rest.",
            purpose = "Improve speed and explosiveness.",
            scope = "200m"
        ),
        Workouts(
            id = 129,
            day = 9,
            mainWorkOut = "3 x 200m with steady pace, 2-minute rest.",
            purpose = "Work on pacing over longer intervals.",
            scope = "200m"
        ),
        Workouts(
            id = 130,
            day = 10,
            mainWorkOut = "5 x 200m at moderate intensity, 1.5-minute rest.",
            purpose = "Further develop endurance at a moderate pace.",
            scope = "200m"
        ),
        Workouts(
            id = 131,
            day = 11,
            mainWorkOut = "5 x 200m sprints at 70% effort, 2 minutes rest.",
            purpose = "Build speed while working at a controlled effort level.",
            scope = "200m"
        ),
        Workouts(
            id = 132,
            day = 12,
            mainWorkOut = "4 x 250m with a steady pace, 2.5-minute rest.",
            purpose = "Improve endurance and pacing over longer intervals.",
            scope = "200m"
        ),
        Workouts(
            id = 133,
            day = 13,
            mainWorkOut = "3 x 300m, maintain moderate effort, 3-minute rest.",
            purpose = "Increase endurance and stamina for longer sprints.",
            scope = "200m"
        ),
        Workouts(
            id = 134,
            day = 14,
            mainWorkOut = "6 x 150m sprints, 90-second rest.",
            purpose = "Improve speed and explosive power.",
            scope = "200m"
        ),
        Workouts(
            id = 135,
            day = 15,
            mainWorkOut = "1 x 300m at a challenging pace, record time and compare to previous week.",
            purpose = "Test your progress with a longer sprint.",
            scope = "200m"
        ),
        Workouts(
            id = 136,
            day = 16,
            mainWorkOut = "5 x 200m at 75% effort, 2 minutes rest.",
            purpose = "Increase effort while maintaining good pacing.",
            scope = "200m"
        ),
        Workouts(
            id = 137,
            day = 17,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Recovery and flexibility.",
            scope = "200m"
        ),
        Workouts(
            id = 138,
            day = 18,
            mainWorkOut = "4 x 250m sprints at 80% effort, 2.5 minutes rest.",
            purpose = "Push intensity and endurance at a longer distance.",
            scope = "200m"
        ),
        Workouts(
            id = 139,
            day = 19,
            mainWorkOut = "3 x 300m with steady pace, 3-minute rest.",
            purpose = "Focus on pacing and endurance over longer sprints.",
            scope = "200m"
        ),
        Workouts(
            id = 140,
            day = 20,
            mainWorkOut = "6 x 200m at 80% effort, 2 minutes rest.",
            purpose = "Improve endurance and pacing at a challenging intensity.",
            scope = "200m"
        ),
        Workouts(
            id = 141,
            day = 21,
            mainWorkOut = "6 x 200m sprints at 85% effort, 2.5-minute rest.",
            purpose = "Build maximum speed and endurance with longer recovery.",
            scope = "200m"
        ),
        Workouts(
            id = 142,
            day = 22,
            mainWorkOut = "5 x 250m with a controlled pace, 2-minute rest.",
            purpose = "Maintain a consistent effort at a slightly higher distance.",
            scope = "200m"
        ),
        Workouts(
            id = 143,
            day = 23,
            mainWorkOut = "4 x 300m sprints with steady pacing, 3-minute rest.",
            purpose = "Focus on pacing and endurance for longer distances.",
            scope = "200m"
        ),
        Workouts(
            id = 144,
            day = 24,
            mainWorkOut = "6 x 200m focusing on endurance, 2-minute rest.",
            purpose = "Improve endurance and stamina with short intervals.",
            scope = "200m"
        ),
        Workouts(
            id = 145,
            day = 25,
            mainWorkOut = "1 x 400m at maximum effort, record time for comparison.",
            purpose = "Test your endurance and speed over a longer distance.",
            scope = "200m"
        ),
        Workouts(
            id = 146,
            day = 26,
            mainWorkOut = "5 x 250m at 85% effort, 2-minute rest.",
            purpose = "Focus on speed and endurance at a high intensity.",
            scope = "200m"
        ),
        Workouts(
            id = 147,
            day = 27,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Flexibility and recovery.",
            scope = "200m"
        ),
        Workouts(
            id = 148,
            day = 28,
            mainWorkOut = "6 x 200m focusing on endurance, 2-minute rest.",
            purpose = "Build stamina with short, high-intensity intervals.",
            scope = "200m"
        ),
        Workouts(
            id = 149,
            day = 29,
            mainWorkOut = "3 x 300m with steady pace, 3.5-minute rest.",
            purpose = "Endurance and pacing over a longer distance.",
            scope = "200m"
        ),
        Workouts(
            id = 150,
            day = 30,
            mainWorkOut = "5 x 250m at 90% effort, 2.5-minute rest.",
            purpose = "Push your limits with high-intensity sprinting.",
            scope = "200m"
        ),
        Workouts(
            id = 151,
            day = 1,
            mainWorkOut = "4 x 400m at an easy pace, 3 minutes rest between intervals.",
            purpose = "Build endurance and form at a relaxed pace.",
            scope = "400m"
        ),
        Workouts(
            id = 152,
            day = 2,
            mainWorkOut = "5 x 300m sprints at a comfortable pace, 2-minute rest.",
            purpose = "Improve speed and endurance with slightly shorter distances.",
            scope = "400m"
        ),
        Workouts(
            id = 153,
            day = 3,
            mainWorkOut = "3 x 400m, focus on steady pacing, 3-minute rest.",
            purpose = "Develop the ability to maintain consistent pacing.",
            scope = "400m"
        ),
        Workouts(
            id = 154,
            day = 4,
            mainWorkOut = "4 x 350m, maintain a relaxed speed, 3-minute rest.",
            purpose = "Work on pacing with slightly reduced effort.",
            scope = "400m"
        ),
        Workouts(
            id = 155,
            day = 5,
            mainWorkOut = "1 x 400m at a moderate effort, assess and record time.",
            purpose = "Test your progress and pacing over 400m.",
            scope = "400m"
        ),
        Workouts(
            id = 156,
            day = 6,
            mainWorkOut = "3 x 400m at moderate intensity, 2.5-minute rest.",
            purpose = "Increase intensity while focusing on form.",
            scope = "400m"
        ),
        Workouts(
            id = 157,
            day = 7,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Focus on recovery and flexibility.",
            scope = "400m"
        ),
        Workouts(
            id = 158,
            day = 8,
            mainWorkOut = "4 x 300m sprints, 2-minute rest.",
            purpose = "Build speed with slightly shorter intervals.",
            scope = "400m"
        ),
        Workouts(
            id = 159,
            day = 9,
            mainWorkOut = "3 x 400m with steady pace, 3-minute rest.",
            purpose = "Maintain consistent pacing over the longer distance.",
            scope = "400m"
        ),
        Workouts(
            id = 160,
            day = 10,
            mainWorkOut = "5 x 400m at moderate intensity, 2.5-minute rest.",
            purpose = "Improve endurance with moderate-intensity sprints.",
            scope = "400m"
        ),
        Workouts(
            id = 161,
            day = 11,
            mainWorkOut = "5 x 400m at 70% effort, 3-minute rest.",
            purpose = "Build stamina and speed at a controlled pace.",
            scope = "400m"
        ),
        Workouts(
            id = 162,
            day = 12,
            mainWorkOut = "4 x 500m with a steady pace, 3-minute rest.",
            purpose = "Improve endurance with slightly longer intervals.",
            scope = "400m"
        ),
        Workouts(
            id = 163,
            day = 13,
            mainWorkOut = "3 x 600m, maintain moderate effort, 3.5-minute rest.",
            purpose = "Build endurance with longer sprints.",
            scope = "400m"
        ),
        Workouts(
            id = 164,
            day = 14,
            mainWorkOut = "6 x 300m sprints, 2-minute rest.",
            purpose = "Improve speed and stamina with short sprints.",
            scope = "400m"
        ),
        Workouts(
            id = 165,
            day = 15,
            mainWorkOut = "1 x 500m at a challenging pace, record time and compare to previous week.",
            purpose = "Test your progress over a longer sprint.",
            scope = "400m"
        ),
        Workouts(
            id = 166,
            day = 16,
            mainWorkOut = "5 x 400m at 75% effort, 3-minute rest.",
            purpose = "Increase intensity and endurance.",
            scope = "400m"
        ),
        Workouts(
            id = 167,
            day = 17,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Recovery and flexibility.",
            scope = "400m"
        ),
        Workouts(
            id = 168,
            day = 18,
            mainWorkOut = "4 x 500m sprints at 80% effort, 3.5-minute rest.",
            purpose = "Push your limits with longer intervals.",
            scope = "400m"
        ),
        Workouts(
            id = 169,
            day = 19,
            mainWorkOut = "3 x 600m with steady pace, 3.5-minute rest.",
            purpose = "Focus on pacing and consistency.",
            scope = "400m"
        ),
        Workouts(
            id = 170,
            day = 20,
            mainWorkOut = "6 x 400m at 80% effort, 3-minute rest.",
            purpose = "Build speed and endurance at a high intensity.",
            scope = "400m"
        ),
        Workouts(
            id = 171,
            day = 21,
            mainWorkOut = "6 x 400m sprints at 85% effort, 3.5-minute rest.",
            purpose = "Improve speed and stamina at a high intensity.",
            scope = "400m"
        ),
        Workouts(
            id = 172,
            day = 22,
            mainWorkOut = "5 x 500m with a controlled pace, 3-minute rest.",
            purpose = "Build endurance with longer intervals.",
            scope = "400m"
        ),
        Workouts(
            id = 173,
            day = 23,
            mainWorkOut = "4 x 600m sprints with steady pacing, 4-minute rest.",
            purpose = "Build endurance for longer sprints with even pacing.",
            scope = "400m"
        ),
        Workouts(
            id = 174,
            day = 24,
            mainWorkOut = "6 x 400m focusing on endurance, 3-minute rest.",
            purpose = "Improve your ability to sustain speed over multiple intervals.",
            scope = "400m"
        ),
        Workouts(
            id = 175,
            day = 25,
            mainWorkOut = "1 x 800m at maximum effort, record time for comparison.",
            purpose = "Test your endurance and speed over a long sprint.",
            scope = "400m"
        ),
        Workouts(
            id = 176,
            day = 26,
            mainWorkOut = "5 x 500m at 85% effort, 3-minute rest.",
            purpose = "Push your speed and endurance limits.",
            scope = "400m"
        ),
        Workouts(
            id = 177,
            day = 27,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Recovery and flexibility.",
            scope = "400m"
        ),
        Workouts(
            id = 178,
            day = 28,
            mainWorkOut = "6 x 400m focusing on endurance, 3-minute rest.",
            purpose = "Build endurance and stamina.",
            scope = "400m"
        ),
        Workouts(
            id = 179,
            day = 29,
            mainWorkOut = "3 x 600m with steady pace, 4-minute rest.",
            purpose = "Focus on pacing and endurance.",
            scope = "400m"
        ),
        Workouts(
            id = 180,
            day = 30,
            mainWorkOut = "5 x 500m at 90% effort, 3.5-minute rest.",
            purpose = "Push your speed and endurance to their limits.",
            scope = "400m"
        ),
        Workouts(
            id = 181,
            day = 1,
            mainWorkOut = "3 x 400m at 60% effort, 3-minute rest.",
            purpose = "Build endurance and pacing awareness.",
            scope = "1500m"
        ),

        Workouts(
            id = 182,
            day = 2,
            mainWorkOut = "4 x 300m at 65% effort, 2.5-minute rest.",
            purpose = "Improve stamina over shorter intervals.",
            scope = "1500m"
        ),

        Workouts(
            id = 183,
            day = 3,
            mainWorkOut = "5 x 200m at 70% effort, 2-minute rest.",
            purpose = "Work on speed while maintaining control.",
            scope = "1500m"
        ),

        Workouts(
            id = 184,
            day = 4,
            mainWorkOut = "2 x 600m at 60% effort, 4-minute rest.",
            purpose = "Increase endurance capacity for longer runs.",
            scope = "1500m"
        ),

        Workouts(
            id = 185,
            day = 5,
            mainWorkOut = "1 x 1500m at 70% effort.",
            purpose = "Establish a baseline for race-day pacing.",
            scope = "1500m"
        ),

        Workouts(
            id = 186,
            day = 6,
            mainWorkOut = "4 x 300m at 70% effort, 2-minute rest.",
            purpose = "Build controlled speed.",
            scope = "1500m"
        ),

        Workouts(
            id = 187,
            day = 7,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Rest and recovery.",
            scope = "1500m"
        ),
        Workouts(
            id = 188,
            day = 8,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Rest and recovery.",
            scope = "1500m"
        ),
        Workouts(
            id = 189,
            day = 9,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Rest and recovery.",
            scope = "1500m"
        ),
        Workouts(
            id = 190,
            day = 10,
            mainWorkOut = "Light stretching or yoga.",
            purpose = "Rest and recovery.",
            scope = "1500m"
        ),
        Workouts(
            id = 191,
            day = 11,
            mainWorkOut = "4 x 400m at 70% effort, 2.5-minute rest.",
            purpose = "Improve endurance and focus on efficient pacing.",
            scope = "1500m"
        ),

        Workouts(
            id = 192,
            day = 12,
            mainWorkOut = "3 x 600m at 65% effort, 4-minute rest.",
            purpose = "Push endurance capacity.",
            scope = "1500m"
        ),

        Workouts(
            id = 193,
            day = 13,
            mainWorkOut = "5 x 200m at 80% effort, 1.5-minute rest.",
            purpose = "Work on speed development and anaerobic capacity.",
            scope = "1500m"
        ),

        Workouts(
            id = 194,
            day = 14,
            mainWorkOut = "3 x 500m at 75% effort, 3-minute rest.",
            purpose = "Sharpen pacing for mid-range distances.",
            scope = "1500m"
        ),

        Workouts(
            id = 195,
            day = 15,
            mainWorkOut = "1 x 1500m time trial.",
            purpose = "Test pacing strategy and progress.",
            scope = "1500m"
        ),

        Workouts(
            id = 196,
            day = 16,
            mainWorkOut = "4 x 400m at 75% effort, 2.5-minute rest.",
            purpose = "Enhance aerobic capacity and running rhythm.",
            scope = "1500m"
        ),

        Workouts(
            id = 197,
            day = 17,
            mainWorkOut = "Light jogging or yoga.",
            purpose = "Active recovery and flexibility.",
            scope = "1500m"
        ),
        Workouts(
            id = 198,
            day = 18,
            mainWorkOut = "Light jogging or yoga.",
            purpose = "Active recovery and flexibility.",
            scope = "1500m"
        ),
        Workouts(
            id = 199,
            day = 19,
            mainWorkOut = "Light jogging or yoga.",
            purpose = "Active recovery and flexibility.",
            scope = "1500m"
        ),  Workouts(
            id = 200,
            day = 20,
            mainWorkOut = "Light jogging or yoga.",
            purpose = "Active recovery and flexibility.",
            scope = "1500m"
        ),
        Workouts(
            id = 201,
            day = 21,
            mainWorkOut = "5 x 400m at 80% effort, 2.5-minute rest.",
            purpose = "Focus on maintaining a strong pace under fatigue.",
            scope = "1500m"
        ),

        Workouts(
            id = 202,
            day = 22,
            mainWorkOut = "4 x 600m at 75% effort, 4-minute rest.",
            purpose = "Increase stamina for sustained speed.",
            scope = "1500m"
        ),

        Workouts(
            id = 203,
            day = 23,
            mainWorkOut = "6 x 200m at 85% effort, 1.5-minute rest.",
            purpose = "Boost finishing speed for the race.",
            scope = "1500m"
        ),

        Workouts(
            id = 204,
            day = 24,
            mainWorkOut = "3 x 800m at 75% effort, 5-minute rest.",
            purpose = "Push endurance threshold for race conditions.",
            scope = "1500m"
        ),

        Workouts(
            id = 205,
            day = 25,
            mainWorkOut = "1 x 1500m time trial at 100% effort.",
            purpose = "Test overall improvement and race readiness.",
            scope = "1500m"
        ),

        Workouts(
            id = 206,
            day = 26,
            mainWorkOut = "4 x 500m at 80% effort, 3-minute rest.",
            purpose = "Maintain high-speed endurance.",
            scope = "1500m"
        ),

        Workouts(
            id = 207,
            day = 27,
            mainWorkOut = "Gentle stretching or yoga.",
            purpose = "Active recovery and flexibility.",
            scope = "1500m"
        ),

        Workouts(
            id = 208,
            day = 28,
            mainWorkOut = "5 x 300m at 85% effort, 2-minute rest.",
            purpose = "Simulate race conditions with high-intensity efforts.",
            scope = "1500m"
        ),

        Workouts(
            id = 209,
            day = 29,
            mainWorkOut = "3 x 800m at 80% effort, 4-minute rest.",
            purpose = "Build confidence in sustained race pace.",
            scope = "1500m"
        ),

        Workouts(
            id = 210,
            day = 30,
            mainWorkOut = "4 x 400m at 85% effort, 2.5-minute rest.",
            purpose = "Final preparation for race-day performance.",
            scope = "1500m"
        )

        )


}