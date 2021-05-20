import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int?,
    var uname: String?,
    var name: String?,
    var avatar: String?,
    var comp: String?,
    var loc: String?,
    var rep: Int? = 0
) : Parcelable
