import androidx.fragment.app.Fragment
import com.development.sotrack.R

class MainFragment : Fragment(R.layout.fragment_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Cover.visibility = ImageView.INVISIBLE
        Button.setOnClickListener {
            Cover.visibility = ImageView.VISIBLE
            urPanicking.visibility = ImageView.VISIBLE
            Cover.setOnClickListener{
                Cover.visibility = ImageView.INVISIBLE
                urPanicking.visibility = ImageView.INVISIBLE
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}
