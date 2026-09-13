package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.SavedPartnerEntity
import com.example.data.local.SavedPlanEntity
import com.example.data.local.SavedSchemeEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.BusinessProfile
import com.example.ui.navigation.Screen
import com.example.ui.theme.GovBlueContainer
import com.example.ui.theme.GovBlueDark
import com.example.ui.theme.GovBlueLight
import com.example.ui.theme.GovBluePrimary
import com.example.ui.theme.GrowthGreen
import com.example.ui.theme.GrowthGreenLight
import com.example.ui.theme.SaffronAccent
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.SlateLight
import com.example.ui.theme.SlateMedium

@Composable
fun ProfileScreen(
    userProfile: UserProfileEntity?,
    businessProfile: BusinessProfile,
    savedSchemes: List<SavedSchemeEntity>,
    savedPlans: List<SavedPlanEntity>,
    savedPartners: List<SavedPartnerEntity>,
    onSelectSchemeById: (String) -> Unit,
    onNavigate: (Screen) -> Unit,
    onUpdateProfile: (String, String, String, String, String, String) -> Unit,
    onRemoveScheme: (String) -> Unit,
    isDarkMode: Boolean = false,
    onToggleDarkMode: () -> Unit = {}
) {
    val context = LocalContext.current
    var showEditDialog by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    val name = userProfile?.fullName ?: "Ramesh Kumar"
    val phone = userProfile?.phone ?: "+91 98765 43210"
    val state = userProfile?.state ?: "Uttar Pradesh"
    val district = userProfile?.district ?: "Varanasi"
    val category = userProfile?.socialCategory ?: "Scheduled Caste (SC)"
    val income = userProfile?.familyIncome ?: "₹1.50 - 3.00 Lakh"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("profile_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Avatar and Profile Header Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(GovBluePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = GovBlueDark
                            )
                            Text(
                                text = phone,
                                fontSize = 12.sp,
                                color = SlateMedium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(GrowthGreenLight)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = category,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GrowthGreen
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = { showEditDialog = true },
                            modifier = Modifier.testTag("edit_profile_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = GovBluePrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Location & Income Pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProfileInfoBox(
                            label = "Location",
                            value = "$district, $state",
                            modifier = Modifier.weight(1f)
                        )
                        ProfileInfoBox(
                            label = "Family Income",
                            value = income,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    ProfileInfoBox(
                        label = "Active Business Target",
                        value = "${businessProfile.businessType} (₹%,d Loan Req.)".format(businessProfile.loanRequired),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Saved Schemes Section
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Saved Government Schemes (${savedSchemes.size})",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = GovBlueDark
                        )
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = GovBluePrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    if (savedSchemes.isEmpty()) {
                        Text(
                            text = "No saved schemes yet. Bookmark schemes from the Schemes tab to review them later.",
                            fontSize = 12.sp,
                            color = SlateMedium,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(8.dp))
                        savedSchemes.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(GovBlueLight)
                                    .clickable {
                                        onSelectSchemeById(item.id)
                                        onNavigate(Screen.SchemeDetail)
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = GovBlueDark
                                    )
                                    Text(
                                        text = "${item.category} • ${item.maxLoan} • ${item.interestRate}",
                                        fontSize = 11.sp,
                                        color = SlateMedium
                                    )
                                }
                                IconButton(
                                    onClick = { onRemoveScheme(item.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remove",
                                        tint = Color(0xFFDC2626),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Saved Action Plans
        if (savedPlans.isNotEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Saved AI Action Plans (${savedPlans.size})",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = GovBlueDark,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        savedPlans.forEach { plan ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFF0FDF4))
                                    .clickable { onNavigate(Screen.ActionPlan) }
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = plan.businessType,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = GrowthGreen
                                    )
                                    Text(
                                        text = plan.summary,
                                        fontSize = 11.sp,
                                        color = SlateMedium,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Preferences & Settings
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Preferences (प्राथमिकताएं)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = GovBlueDark,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    // Notifications Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Notifications, contentDescription = null, tint = GovBluePrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Scheme & Subsidy Notifications", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                                Text("Updates on new loan tranches & camps", fontSize = 11.sp, color = SlateLight)
                            }
                        }
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = GovBluePrimary)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Theme Light/Dark Mode Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = if (isDarkMode) Color(0xFFFBBF24) else GovBluePrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isDarkMode) "Dark Theme Enabled" else "Light Theme (Bright Mode)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isDarkMode) "Switch to crisp white high-contrast theme" else "Switch to comfortable night theme",
                                    fontSize = 11.sp,
                                    color = SlateLight
                                )
                            }
                        }
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { onToggleDarkMode() },
                            colors = SwitchDefaults.colors(checkedThumbColor = GovBluePrimary),
                            modifier = Modifier.testTag("theme_switch_preferences")
                        )
                    }
                }
            }
        }

        // Official Government Portals & Support Desk
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = GovBluePrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Official Government Links & Support",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = GovBlueDark
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    GovernmentLinkItem(
                        title = "NSFDC National Portal (nsfdc.nic.in)",
                        url = "https://nsfdc.nic.in"
                    )
                    GovernmentLinkItem(
                        title = "Ministry of Social Justice & Empowerment",
                        url = "https://socialjustice.gov.in"
                    )
                    GovernmentLinkItem(
                        title = "Pradhan Mantri MUDRA Yojana (mudra.org.in)",
                        url = "https://www.mudra.org.in"
                    )
                    GovernmentLinkItem(
                        title = "Stand-Up India Portal (standupmitra.in)",
                        url = "https://www.standupmitra.in"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // National Helpline Card
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(GrowthGreenLight)
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("National Toll-Free Helpline:", fontSize = 11.sp, color = Color(0xFF14532D))
                                Text("1800-11-2001 (Toll Free)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF14532D))
                            }
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:1800112001"))
                                    context.startActivity(intent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = GrowthGreen),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Call", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    // Edit Profile Modal Dialog
    if (showEditDialog) {
        var editName by remember { mutableStateOf(name) }
        var editPhone by remember { mutableStateOf(phone) }
        var editState by remember { mutableStateOf(state) }
        var editDistrict by remember { mutableStateOf(district) }
        var editCategory by remember { mutableStateOf(category) }
        var editIncome by remember { mutableStateOf(income) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = {
                Text(
                    text = "Update User Profile",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = { editPhone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editDistrict,
                        onValueChange = { editDistrict = it },
                        label = { Text("District") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editState,
                        onValueChange = { editState = it },
                        label = { Text("State") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateProfile(editName, editPhone, editState, editDistrict, editCategory, editIncome)
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GovBluePrimary)
                ) {
                    Text("Save Changes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileInfoBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GovBlueLight)
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Column {
            Text(text = label, fontSize = 10.sp, color = SlateLight)
            Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GovBlueDark)
        }
    }
}

@Composable
fun GovernmentLinkItem(title: String, url: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 12.sp, color = GovBluePrimary, fontWeight = FontWeight.Medium)
        Icon(imageVector = Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, tint = GovBluePrimary, modifier = Modifier.size(14.dp))
    }
}
